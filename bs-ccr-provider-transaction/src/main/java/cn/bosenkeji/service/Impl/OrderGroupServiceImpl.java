package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.OrderGroupMapper;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.OrderGroupService;
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
import com.aliyun.opensearch.sdk.generated.search.Config;
import com.aliyun.opensearch.sdk.generated.search.SearchFormat;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchParamsBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

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

    private static final String appName = "bs_ccr_trade_dev";
    private static final String orderGroupTable="order_group";

    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int coinPairChoiceId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(this.orderGroupMapper.findAll(coinPairChoiceId));
    }

    @Override
    public OrderGroup getOneById(int orderGroupId) {
        OrderGroup orderGroup = this.orderGroupMapper.selectByPrimaryKey(orderGroupId);
        if (orderGroup != null){
            double endProfitRatio = orderGroup.getEndProfitRatio() / CommonConstantUtil.ACCURACY;

            if (orderGroup.getCoinPairChoice() != null){
                CoinPair coinPair = this.iCoinPairClientService.getCoinPair(orderGroup.getCoinPairChoice().getCoinPartnerId());
                orderGroup.getCoinPairChoice().setCoinPair(coinPair);
            }

            orderGroup.setEndProfitRatio(endProfitRatio);
        }
        return orderGroup;
    }

    @Override
    public Optional<Integer> add(OrderGroup orderGroup) {
        double endProfitRatio = orderGroup.getEndProfitRatio() * CommonConstantUtil.ACCURACY;

        orderGroup.setEndProfitRatio(endProfitRatio);
        orderGroup.setStatus(CommonConstantUtil.ACTIVATE_STATUS);
        this.orderGroupMapper.insertSelective(orderGroup);
        pushToOpenSearch(orderGroup.getId());
        return Optional.of(orderGroup.getId());
    }

    public boolean pushToOpenSearch(int orderGroupId) {

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
        System.out.println("jsonList = " + jsonList);



        try {
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
            double endProfitRatio = orderGroup.getEndProfitRatio() * CommonConstantUtil.ACCURACY;

            orderGroup.setEndProfitRatio(endProfitRatio);
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
    public Optional<Integer> checkExistByID(int orderGroupId) {
        return Optional.of(this.orderGroupMapper.checkExistById(orderGroupId));
    }

    @Override
    public List<OrderGroupOpenSearchFormat> searchTradeRecordByCondition(Long startTime, Long endTime, int coinPairChoiceId) {

        List<OrderGroupOpenSearchFormat> orderGroupOpenSearchFormats = new ArrayList<>();
        if (startTime > endTime ){
            return orderGroupOpenSearchFormats;
        }

        SearcherClient searcherClient = new SearcherClient(openSearchClient);

        Config config = new Config(Lists.newArrayList(appName));
        config.setSearchFormat(SearchFormat.FULLJSON);
        config.setStart(0);
        config.setFetchFields(CommonConstantUtil.openSearchFetchFieldFormat);

        SearchParams searchParams = new SearchParams(config);
        String searchString;
        if (startTime > 0 && endTime > 0 ){
            searchString = "coin_pair_choice_id:'"+coinPairChoiceId+"'"+" AND "+"created_time:["+startTime+","+endTime+"]";
        }else {
            searchString = "coin_pair_choice_id:'"+coinPairChoiceId+"'";
        }

        searchParams.setQuery(searchString);
        SearchParamsBuilder paramsBuilder = SearchParamsBuilder.create(searchParams);

        try {
            SearchResult execute = searcherClient.execute(paramsBuilder);
            String result = execute.getResult();
            OpenSearchExecuteResult openSearchExecuteResult = JSONObject.parseObject(result,OpenSearchExecuteResult.class);
            List<OpenSearchField> items = openSearchExecuteResult.getResult().getItems();

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
                        toCollection(() -> new TreeSet<>(comparingLong(OrderGroupOpenSearchFormat::getId))), ArrayList::new)
        );

        return unique;
    }

    @Override
    public List<OrderGroup> partFindByCoinPairChoiceIds(List<Integer> coinPairChoiceIds) {
        if (CollectionUtils.isEmpty(coinPairChoiceIds)){
            return null;
        }
        return this.orderGroupMapper.partFindByCoinPairChoiceIds(coinPairChoiceIds);
    }

}
