package cn.bosenkeji.service.Impl;

import cn.bosenkeji.OpenSearchPage;
import cn.bosenkeji.interfaces.CommonResultNumberEnum;
import cn.bosenkeji.mapper.OrderGroupMapper;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.OrderGroupService;
import cn.bosenkeji.service.TradeOrderService;
import cn.bosenkeji.util.CommonConstantUtil;
import cn.bosenkeji.vo.OpenSearchFormat;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.transaction.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.search.*;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchParamsBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author CAJR
 * @date 2019/11/4 3:23 下午
 */
@Service
public class OrderGroupServiceImpl implements OrderGroupService {

    @Resource
    OrderGroupMapper orderGroupMapper;

    @Resource
    ICoinPairClientService iCoinPairClientService;

    @Resource
    private DocumentClient documentClient;

    @Resource
    OpenSearchClient openSearchClient;

    @Resource
    TradeOrderService tradeOrderService;

    @Resource
    CoinPairChoiceService coinPairChoiceService;

    @Value("${aliyun.open-search.app-name}")
    private String appName;

    private static final String orderGroupTable="order_group";


    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int coinPairChoiceId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(this.orderGroupMapper.findAll(coinPairChoiceId));
    }

    @Override
    public Optional<Integer> checkExistByCoinPairChoiceIdAndIsEnd(int coinPairChoiceId,int isEnd) {
        return Optional.of(this.orderGroupMapper.checkExistByCoinPairChoiceIdAndIsEnd1(coinPairChoiceId,isEnd));
    }


    @Override
    public OrderGroup getOneById(int orderGroupId) {
        OrderGroup orderGroup = this.orderGroupMapper.selectByPrimaryKey(orderGroupId);

        if (orderGroup != null){
            int buildNumbers = 0;
            double accumulateSell = 0,accumulateCast = 0,accumulateProfit = 0;

            orderGroup.setTradeOrders(tradeOrderService.listByOrderGroupId(orderGroup.getId()).stream().sorted(Comparator.comparing(TradeOrder::getCreatedAt).reversed()).collect(Collectors.toList()));
            double endProfitRatio = orderGroup.getEndProfitRatio() / CommonConstantUtil.ACCURACY;

            if (orderGroup.getCoinPairChoice() != null){
                CoinPair coinPair = this.iCoinPairClientService.getCoinPair(orderGroup.getCoinPairChoice().getCoinPairId());
                orderGroup.getCoinPairChoice().setCoinPair(coinPair);
            }else {
                return null;
            }
            orderGroup.setEndProfitRatio(endProfitRatio);
            if (orderGroup.getTradeOrders().size() != 0){
                List<TradeOrder> tradeOrders = orderGroup.getTradeOrders();
                for (TradeOrder o : tradeOrders) {
                    //把ai止盈的交易数量累加
                    if (o.getTradeType() == CommonConstantUtil.AI_STOP_PROFIT_STATUS){
                        accumulateProfit = o.getSellProfit();
                        accumulateSell += o.getTradeNumbers();
                    }
                    if (o.getTradeType() == CommonConstantUtil.BUILD_STATUS){
                        buildNumbers += 1;
                        accumulateCast += o.getTradeCost();
                    }
                }
            }
            orderGroup.setBuildNumbers(buildNumbers);
            orderGroup.setTotalCast(Math.abs(accumulateCast));
            orderGroup.setTotalSell(accumulateSell);
            orderGroup.setTotalProfit(accumulateProfit);
        }
        return orderGroup;
    }

    @Override
    public Optional<Integer> add(OrderGroup orderGroup) {

        double endProfitRatio = orderGroup.getEndProfitRatio() * CommonConstantUtil.ACCURACY_RATIO;

        orderGroup.setEndProfitRatio(endProfitRatio);
        orderGroup.setStatus(CommonConstantUtil.ACTIVATE_STATUS);
        orderGroup.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        this.orderGroupMapper.insertSelective(orderGroup);
        pushToOpenSearch(orderGroup.getId());
        return Optional.of(orderGroup.getId());
    }

    public boolean pushToOpenSearch(int orderGroupId) {

        try {
        OrderGroup orderGroup = getOneById(orderGroupId);
        if(null == orderGroup)
            return false;
        orderGroup.setTradeOrders(null);
        orderGroup.setCoin_pair_choice(JSON.toJSONString(orderGroup.getCoinPairChoice()));
        orderGroup.setCoinPairChoice(null);
        OpenSearchFormat<OrderGroup> format=new OpenSearchFormat();

        format.setFields(orderGroup);
        format.setCmd("ADD");

        List<OpenSearchFormat> list=new ArrayList<>();
        list.add(format);

        String jsonList = JSON.toJSONString(list);
        //System.out.println("jsonList = " + jsonList);

            //System.out.println("APPNAME"+appName);
            OpenSearchResult pushResult = documentClient.push(jsonList, appName, orderGroupTable);
            if(pushResult.getResult().equalsIgnoreCase("true")) {
                System.out.println("pushResult = " + pushResult+"推送成功");
                return true;
            }else {
                System.out.println("push 失败 "+pushResult.getTraceInfo());
                return false;
            }

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Optional<Integer> update(OrderGroup orderGroup) {
            double endProfitRatio = orderGroup.getEndProfitRatio() * CommonConstantUtil.ACCURACY_RATIO;

            if (orderGroup.getEndType() == CommonConstantUtil.FORGET_ORDER){
                TradeOrder tradeOrder = new TradeOrder();
                tradeOrder.setOrderGroupId(orderGroup.getId());
                tradeOrder.setTradeType(CommonConstantUtil.FORGET_ORDER_TYPE);
                tradeOrder.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                tradeOrder.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                this.tradeOrderService.add(tradeOrder);
            }
            orderGroup.setEndProfitRatio(endProfitRatio);
            orderGroup.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            Integer result = this.orderGroupMapper.updateByPrimaryKeySelective(orderGroup);
            pushToOpenSearch(orderGroup.getId());
            return Optional.of(result);
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(this.orderGroupMapper.updateStatusByPrimaryKey(id,CommonConstantUtil.DELETE_STATUS, Timestamp.valueOf(LocalDateTime.now())));
    }

    @Override
    public Optional<Integer> checkExistByCoinPairChoiceIdAndIsEnd(int coinPairChoiceId) {
        return Optional.of(this.orderGroupMapper.checkExistByCoinPairChoiceIdAndIsEnd(coinPairChoiceId));
    }

    @Override
    public Optional<Integer> resultNotEndGroupId(int coinPairChoiceId) {
        return Optional.of(this.orderGroupMapper.resultNotEndGroupId(coinPairChoiceId));
    }

    @Override
    public Optional<Integer> checkExistByID(int orderGroupId) {
        return Optional.of(this.orderGroupMapper.checkExistById(orderGroupId));
    }

    @Override
    public Optional<Integer> checkExistByGroupName(String name) {
        return Optional.of(this.orderGroupMapper.checkExistByName(name));
    }

    @Override
    public OpenSearchPage searchTradeRecordByCondition(Long startTime, Long endTime, int coinPairChoiceId, int pageNum, int pageSize) {

        List<OrderGroupOpenSearchFormat> orderGroupOpenSearchFormats = new ArrayList<>();
        if (startTime > endTime ){
            return null;
        }

        OpenSearchPage page= new OpenSearchPage();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.countStartRow();

        SearcherClient searcherClient = new SearcherClient(openSearchClient);

        Config config = new Config(Lists.newArrayList(appName));
        config.setSearchFormat(SearchFormat.FULLJSON);
        config.setStart(page.getStartRow());
        config.setHits(page.getPageSize());
        config.setFetchFields(CommonConstantUtil.openSearchFetchFieldFormat);

        SearchParams searchParams = new SearchParams(config);
        String searchString;
        if (startTime > 0){
            searchString = "coin_pair_choice_id:'"+coinPairChoiceId+"'"+" AND "+"created_time:["+startTime+","+endTime+"]" + CommonConstantUtil.DISTINCT_STATEMENT;
        }else {
            searchString = "coin_pair_choice_id:'"+coinPairChoiceId+"'" + CommonConstantUtil.DISTINCT_STATEMENT;
        }
        Sort sort = new Sort();
        sort.addToSortFields(new SortField("created_time",Order.DECREASE));
        searchParams.setSort(sort);
        searchParams.setQuery(searchString);
        SearchParamsBuilder paramsBuilder = SearchParamsBuilder.create(searchParams);

        try {
            SearchResult execute = searcherClient.execute(paramsBuilder);
            String result = execute.getResult();
            OpenSearchExecuteResult openSearchExecuteResult = JSONObject.parseObject(result,OpenSearchExecuteResult.class);
            List<OpenSearchField> items = openSearchExecuteResult.getResult().getItems();
            page.setTotal(openSearchExecuteResult.getResult().getTotal());

            items.forEach(openSearchField -> {
                OpenSearchOrderVo openSearchOrderVo = openSearchField.getFields();
                OrderGroupOpenSearchFormat orderGroupOpenSearchFormat = new OrderGroupOpenSearchFormat();

                orderGroupOpenSearchFormat.setId(openSearchOrderVo.getOrderGroupId());
                orderGroupOpenSearchFormat.setName(openSearchOrderVo.getName());
                orderGroupOpenSearchFormat.setCoinPairChoiceId(openSearchOrderVo.getCoinPairChoiceId());
                orderGroupOpenSearchFormat.setCreatedAt(openSearchOrderVo.getCreatedTime());
                orderGroupOpenSearchFormats.add(orderGroupOpenSearchFormat);
            });
        } catch (OpenSearchException | OpenSearchClientException e) {
            e.printStackTrace();
        }


        //去重
        List<OrderGroupOpenSearchFormat> unique = orderGroupOpenSearchFormats.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparingLong(OrderGroupOpenSearchFormat::getId))), ArrayList::new));
        page.setList(unique);
        page.countTotalPages();
        return page;
    }

    @Override
    public List<OrderGroup> partFindByCoinPairChoiceIds(List<Integer> coinPairChoiceIds) {
        if (CollectionUtils.isEmpty(coinPairChoiceIds)){
            return null;
        }
        return this.orderGroupMapper.partFindByCoinPairChoiceIds(coinPairChoiceIds);
    }

    @Override
    public Integer updateOpenSearchFromSql(int id) {
        return pushToOpenSearch(id) ? CommonResultNumberEnum.SUCCESS :CommonResultNumberEnum.FAIL;
    }

    @Override
    public OrderGroupOverviewResult tradeOverview(int coinPairChoiceId) {
        OrderGroupOverviewResult orderGroupOverviewResult = new OrderGroupOverviewResult();
        List<OrderGroup> orderGroups = this.orderGroupMapper.findAllByCoinPairChoiceIdAndIsEnd(coinPairChoiceId,CommonConstantUtil.END);

        if (!CollectionUtils.isEmpty(orderGroups)){
            int endNumber = orderGroups.size();
            double totalProfit = 0,trackProfit = 0;
            String coinPairChoiceName = this.coinPairChoiceService.get(coinPairChoiceId).getCoinPair().getName();

            for (OrderGroup o : orderGroups) {
                List<TradeOrder> tradeOrders = o.getTradeOrders();
                for (TradeOrder t :
                        tradeOrders) {
                    if (t.getTradeType() == 2 || t.getTradeType() == 3 ){
                        totalProfit += t.getSellProfit();
                        trackProfit += t.getExtraProfit();
                    }
                }
            }
            orderGroupOverviewResult.setCoinPairName(coinPairChoiceName);
            orderGroupOverviewResult.setEndNumbers(endNumber);
            orderGroupOverviewResult.setTotalProfit(totalProfit / CommonConstantUtil.ACCURACY);
            orderGroupOverviewResult.setTrackProfit(trackProfit / CommonConstantUtil.ACCURACY);
        }
        return orderGroupOverviewResult;
    }


    @Override
    public OrderGroup getByCoinPairChoiceId(int coinPairChoiceId) {
        if (coinPairChoiceId <= 0){
            return null;
        }
        List<OrderGroup> orderGroups = this.orderGroupMapper.findAllByCoinPairChoiceIdAndIsEnd(coinPairChoiceId,CommonConstantUtil.NOT_END);
        if (CollectionUtils.isEmpty(orderGroups)){
            return new OrderGroup();
        }
        orderGroups = orderGroups.stream().sorted(Comparator.comparing(OrderGroup::getCreatedAt).reversed()).collect(Collectors.toList());
        return getOneById(orderGroups.get(0).getId());
    }

    @Override
    public int getGroupIdByName(String name) {
        return this.orderGroupMapper.getIdByName(name);
    }

    @Override
    public OrderGroup getOneByName(String name) {
        return this.orderGroupMapper.getOneByName(name);
    }

}
