package cn.bosenkeji.service.Impl;

import cn.bosenkeji.OpenSearchPage;
import cn.bosenkeji.interfaces.TradeTypeInterface;
import cn.bosenkeji.mapper.TradeOrderMapper;
import cn.bosenkeji.service.TradeOrderService;
import cn.bosenkeji.util.CommonConstantUtil;
import cn.bosenkeji.vo.OpenSearchFormat;
import cn.bosenkeji.vo.transaction.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.search.Aggregate;
import com.aliyun.opensearch.sdk.generated.search.Config;
import com.aliyun.opensearch.sdk.generated.search.SearchFormat;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchParamsBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author CAJR
 * @date 2019/11/4 3:24 下午
 */
@Service
public class TradeOrderServiceImpl implements TradeOrderService {


    @Value("${aliyun.open-search.app-name}")
    private String appName;
    private static final String orderTable="trade_order";

    //查询字段
    private static final ArrayList<String> openSearchFetchField = Lists.newArrayList("id","order_group_id","trade_average_price","trade_numbers","trade_cost","shell_profit","trade_type","status","created_at","name","coin_pair_choice_id","coin_pair_choice","end_profit_ratio","is_end","end_type","trade_profit_price");

    private static final String addCmd = "ADD";

    //默认分页
    private static final int commonConfigStart = 1;
    private static final int commonConfigHits = 10;
    //query 语句相关
    private static final String tradeTypeGroupKey = "trade_type";
    private static final String sumTotalTradeCostAggFun = "count()#sum(trade_cost)";
    private static final String sumTotalShellProfitAggFun = "count()#sum(shell_profit)";
    private static final String sumTotalExtraProfitAggFun= "count()#sum(extra_profit)";
    private static final String orCoinPairChoiceQuery = " OR coin_pair_choice_id:'";
    private static final String andTradeTypeQuery = " AND trade_type:'";

    @Resource
    TradeOrderMapper tradeOrderMapper;

    @Resource
    private DocumentClient documentClient;


    @Resource
    private SearcherClient searcherClient;

    //
    private static final int SHELL_STATUS=2;

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TradeOrder> tradeOrders = this.tradeOrderMapper.findAll();
        if (!CollectionUtils.isEmpty(tradeOrders)){
            tradeOrders.forEach(this::reverseTransformNumericalValue);
        }
        return new PageInfo<>(tradeOrders);
    }

    @Override
    public List<TradeOrder> listByOrderGroupId(int orderGroupId) {
        List<TradeOrder> tradeOrders = this.tradeOrderMapper.findAllByOrderGroupId(orderGroupId);
        if (!CollectionUtils.isEmpty(tradeOrders)){
            tradeOrders.forEach(this::reverseTransformNumericalValue);
        }
        return tradeOrders;
    }

    @Override
    public TradeOrder getById(int tradeOrderId) {
        TradeOrder tradeOrderResult =  this.tradeOrderMapper.selectByPrimaryKey(tradeOrderId);
        if (tradeOrderResult != null){
            reverseTransformNumericalValue(tradeOrderResult);
        }
        return tradeOrderResult;
    }

    private void transformNumericalValue(TradeOrder tradeOrder){
        double tradeAveragePrice = tradeOrder.getTradeAveragePrice()* CommonConstantUtil.ACCURACY;
        double tradeNumbers = tradeOrder.getTradeNumbers()* CommonConstantUtil.ACCURACY;
        double tradeCost = tradeOrder.getTradeCost()* CommonConstantUtil.ACCURACY;
        double shellProfit = tradeOrder.getShellProfit()* CommonConstantUtil.ACCURACY;
        double extraProfit = tradeOrder.getExtraProfit() * CommonConstantUtil.ACCURACY;

        tradeOrder.setStatus(CommonConstantUtil.ACTIVATE_STATUS);
        tradeOrder.setTradeAveragePrice(tradeAveragePrice);
        tradeOrder.setTradeNumbers(tradeNumbers);
        tradeOrder.setTradeCost(tradeCost);
        tradeOrder.setShellProfit(shellProfit);
        tradeOrder.setExtraProfit(extraProfit);
        tradeOrder.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }


    private void reverseTransformNumericalValue(TradeOrder tradeOrder){
        double tradeAveragePrice = tradeOrder.getTradeAveragePrice() / CommonConstantUtil.ACCURACY;
        double tradeNumbers = tradeOrder.getTradeNumbers() / CommonConstantUtil.ACCURACY;
        double tradeCost = tradeOrder.getTradeCost() / CommonConstantUtil.ACCURACY;
        double shellProfit = tradeOrder.getShellProfit() / CommonConstantUtil.ACCURACY;
        double extraProfit = tradeOrder.getExtraProfit() / CommonConstantUtil.ACCURACY;

        tradeOrder.setTradeAveragePrice(tradeAveragePrice);
        tradeOrder.setTradeNumbers(tradeNumbers);
        tradeOrder.setTradeCost(tradeCost);
        tradeOrder.setShellProfit(shellProfit);
        tradeOrder.setExtraProfit(extraProfit);
    }

    @Override
    public Optional<Integer> add(TradeOrder tradeOrder) {
        transformNumericalValue(tradeOrder);
        Optional<Integer> integer = Optional.of(this.tradeOrderMapper.insertSelective(tradeOrder));
        pushToOpenSearch(tradeOrder.getId());
        return integer;
    }

    /**
     * 通过 交易订单 id 把对应的 交易订单信息 推送到阿里云openSearch
     * @param tradeOrderId
     * @return
     */
    public boolean pushToOpenSearch(int tradeOrderId) {

        try {
        if(tradeOrderId <= 0)
            return false;
        OpenSearchFormat<TradeOrder> field=new OpenSearchFormat<>();
        TradeOrder tradeOrder = getById(tradeOrderId);
        if(null == tradeOrder)
            return false;

        field.setFields(tradeOrder);
        field.setCmd(addCmd);

        List<OpenSearchFormat> list=new ArrayList<>();
        list.add(field);
        String jsonList = JSON.toJSONString(list);

            OpenSearchResult pushResult = documentClient.push(jsonList, appName, orderTable);
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

    /**
     * 多条件 并行搜索 交易记录
     * 多个自选币id、开始结束时间戳、交易类型、分页
     * @param orderSearchRequestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public OpenSearchPage searchTradeOrderByCondition(OrderSearchRequestVo orderSearchRequestVo,int pageNum,int pageSize) {


        OpenSearchPage page= new OpenSearchPage();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.countStartRow();

        if(org.apache.commons.lang.StringUtils.isBlank(orderSearchRequestVo.getCoinPairChoiceIds()))
            return page;

        Config config = setOpenSearchConfig(page.getStartRow(), page.getPageSize());

        SearchParams searchParams = new SearchParams(config);
        StringBuffer otherBuffer = new StringBuffer();
        StringBuffer queryBuffer = new StringBuffer();

        //设置 自选币 条件
        String[] coin_ids = orderSearchRequestVo.getCoinPairChoiceIds().split(",");
        for (String coin_id : coin_ids) {

            Integer integer = Integer.valueOf(coin_id);
            if(null != integer && integer>0) {
                queryBuffer.append(orCoinPairChoiceQuery+integer+"' ");
            }
        }
        String query="("+queryBuffer.toString().replaceFirst("OR","")+")";


        if(org.apache.commons.lang.StringUtils.isBlank(queryBuffer.toString())) {
            return page;
        }

        //设置交易类型 0默认全部不处理 1买入 2卖出 （其中卖出又分为 2 ai止盈 3手动清仓 ）
        Integer tradeType = orderSearchRequestVo.getTradeType();
        if(null != tradeType && tradeType >0) {
            //ai止盈 和 手动清仓
            if(tradeType == TradeTypeInterface.SHELL) {
                otherBuffer.append(" AND ( trade_type:'"+TradeTypeInterface.aiStopProfit+"' OR trade_type:'"+TradeTypeInterface.manualClearRepository+"')");
            }
            // 其他 如 1 ai建仓
            else {
                otherBuffer.append(andTradeTypeQuery + tradeType + "' ");
            }
        }

        //处理时间
        Long startTime = orderSearchRequestVo.getStartTime();
        Long endTime = orderSearchRequestVo.getEndTime();
        if(null != startTime && null!= endTime && startTime >0 && endTime > 0) {

            otherBuffer.append(" AND created_at:[" + orderSearchRequestVo.getStartTime() + ","+orderSearchRequestVo.getEndTime()+"] ");
        }

        if(StringUtils.isNotBlank(otherBuffer.toString())) {
            query = query+otherBuffer.toString();
        }

        searchParams.setQuery(query);

        SearchParamsBuilder searchParamsBuilder = SearchParamsBuilder.create(searchParams);

        try{
            OpenSearchExecuteResult openSearchExecuteResult = executeOpenSearch(searcherClient, searchParamsBuilder);

            List<OpenSearchField> items = openSearchExecuteResult.getResult().getItems();
            List<TradeOrderLogVo> tradeOrderLogVos=new ArrayList<>();
            for (OpenSearchField item : items) {

                if(null != item.getFields()) {
                    TradeOrderLogVo orderLogVo = new TradeOrderLogVo();
                    CoinPairChoice coinPairChoice = null;
                    try {
                        coinPairChoice = JSONObject.parseObject(item.getFields().getCoin_pair_choice(), CoinPairChoice.class);

                        orderLogVo.setCoinPairName(coinPairChoice.getCoinPair().getName());
                        orderLogVo.setCoinPairId(coinPairChoice.getCoinPartnerId());
                        orderLogVo.setCoinPairChoiceId(coinPairChoice.getId());

                    } catch (Exception e) {
                        //不处理
                    }
                    orderLogVo.setId(item.getFields().getId());
                    orderLogVo.setCreateAt(item.getFields().getCreatedAt());
                    orderLogVo.setTradeNumbers(item.getFields().getTradeNumbers());
                    orderLogVo.setShellProfit(item.getFields().getShellProfit());
                    orderLogVo.setTradeCost(item.getFields().getTradeCost());
                    orderLogVo.setEndType(item.getFields().getEndType());
                    orderLogVo.setEndProfitRatio(item.getFields().getEndProfitRatio());
                    orderLogVo.setTradeType(item.getFields().getTradeType());
                    tradeOrderLogVos.add(orderLogVo);
                }
            }

            page.setList(tradeOrderLogVos);
            page.setTotal(openSearchExecuteResult.getResult().getTotal());
            return page;

        }catch (Exception e) {
            e.printStackTrace();
            return page;
        }

    }

    /**
     * 聚合搜索 交易费用 总数 方法
     * 当tradeType 为1 时表示累计买入费用
     * 当tradeType 为2 时表示累计卖出费用
     * @param coinPairChoiceIds
     * @param tradeType
     * @return
     */
    @Override
    public SumTradeCostAggregateVo searchAggregateTradeOrderSumTradeCostByCondition(String coinPairChoiceIds, Integer tradeType) {


        SumTradeCostAggregateVo sumTradeCostAggregateVo = new SumTradeCostAggregateVo();

        if(org.apache.commons.lang.StringUtils.isBlank(coinPairChoiceIds))
            return sumTradeCostAggregateVo;

        Config config = setOpenSearchConfig(commonConfigStart, commonConfigHits);
        SearchParams searchParams = new SearchParams(config);
        StringBuffer otherBuffer = new StringBuffer();
        StringBuffer queryBuffer = new StringBuffer();

        //设置 自选币 条件
        String[] coin_ids = coinPairChoiceIds.split(",");
        for (String coin_id : coin_ids) {

            Integer integer = Integer.valueOf(coin_id);
            if(null != integer && integer>0) {
                queryBuffer.append(orCoinPairChoiceQuery+integer+"' ");
            }
        }
        String query="("+queryBuffer.toString().replaceFirst("OR","")+")";


        if(org.apache.commons.lang.StringUtils.isBlank(queryBuffer.toString())) {
            return sumTradeCostAggregateVo;
        }

        //设置交易类型 0默认全部不处理 1买入 2卖出
        if(null != tradeType && tradeType >0) {
            //ai止盈 和 手动清仓
            if(tradeType == TradeTypeInterface.SHELL) {
                otherBuffer.append(" AND ( trade_type:'"+TradeTypeInterface.aiStopProfit+"' OR trade_type:'"+TradeTypeInterface.manualClearRepository+"')");
            }
            // 其他 如 1 ai建仓
            else {
                otherBuffer.append(andTradeTypeQuery + tradeType + "' ");
            }
        }

        if(StringUtils.isNotBlank(otherBuffer.toString())) {
            query = query+otherBuffer.toString();
        }

        searchParams.setQuery(query);

        System.out.println("the query is :"+query);

        Aggregate aggregate = setOpenSearchAggregate(tradeTypeGroupKey, sumTotalTradeCostAggFun);
        searchParams.addToAggregates(aggregate);


        SearchParamsBuilder searchParamsBuilder = SearchParamsBuilder.create(searchParams);

        try{
            OpenSearchExecuteResult openSearchExecuteResult = executeOpenSearch(searcherClient, searchParamsBuilder);

            List<OpenSearchField> items1 = openSearchExecuteResult.getResult().getItems();
            List<OpenSearchFacet> facet = openSearchExecuteResult.getResult().getFacet();
            for (OpenSearchFacet openSearchFacet : facet) {

                List<OpenSearchFacetItem> items = openSearchFacet.getItems();
                for (OpenSearchFacetItem item : items) {
                    if(item.getValue().equals(String.valueOf(tradeType)))
                    {

                        if(null != item.getCount())
                            sumTradeCostAggregateVo.setCount(Long.parseLong(item.getCount()));
                        if(null != item.getSum())
                            sumTradeCostAggregateVo.setTotalTradeCost(Double.parseDouble(item.getSum()));
                        if(null != item.getValue())
                            sumTradeCostAggregateVo.setValue(item.getValue());
                        return sumTradeCostAggregateVo;
                    }
                }

            }

            return new SumTradeCostAggregateVo();

        }catch (Exception e) {
            e.printStackTrace();
            return new SumTradeCostAggregateVo();
        }

    }

    /**
     * 实现功能： 通过多个自选币id 查询 收益总结的 总单数、合计盈利、追踪盈利
     * 由于openSearch 的aggregate 不够友好 （ 不支持查询全部，即必须有分页；聚合查询的多个sum运算结果 返回字段名均为sum ），分成了两次请求
     * 分别聚合搜索 合计盈利 和 追踪盈利
     * @param coinPairChoiceIds
     * @return
     */
    @Override
    public SumShellProfitAggregateVo searchAggregateTradeOrderSumShellProfitByCondition(String coinPairChoiceIds) {

        SumShellProfitAggregateVo sumShellProfitAggregateVo = new SumShellProfitAggregateVo();

        if(org.apache.commons.lang.StringUtils.isBlank(coinPairChoiceIds))
            return sumShellProfitAggregateVo;

        Config config = setOpenSearchConfig(commonConfigStart, commonConfigHits);

        SearchParams searchParams = new SearchParams(config);
        StringBuffer otherBuffer = new StringBuffer();
        StringBuffer queryBuffer = new StringBuffer();

        //设置 自选币 条件
        String[] coin_ids = coinPairChoiceIds.split(",");
        for (String coin_id : coin_ids) {

            Integer integer = Integer.valueOf(coin_id);
            if(null != integer && integer>0) {
                queryBuffer.append(orCoinPairChoiceQuery+integer+"' ");
            }
        }
        String query="("+queryBuffer.toString().replaceFirst("OR","")+")";


        if(org.apache.commons.lang.StringUtils.isBlank(queryBuffer.toString())) {
            return sumShellProfitAggregateVo;
        }

        //设置交易类型
        //ai止盈 和 手动清仓
        //otherBuffer.append(" AND ( trade_type:'"+TradeTypeInterface.aiStopProfit+"' OR trade_type:'"+TradeTypeInterface.manualClearRepository+"')");

        if(StringUtils.isNotBlank(otherBuffer.toString())) {
            query = query+otherBuffer.toString();
        }

        searchParams.setQuery(query);

        System.out.println("the query is :"+query);

        //聚合搜索 合计盈利
        Aggregate aggregate = setOpenSearchAggregate(tradeTypeGroupKey, sumTotalShellProfitAggFun);
        //aggregate.setAggFilter("trade_teyp:'2' OR trade_type:'3' ");
        searchParams.addToAggregates(aggregate);


        SearchParamsBuilder searchParamsBuilder = SearchParamsBuilder.create(searchParams);
        searchParamsBuilder.addFilter(" trade_type= 2 ","AND");
        searchParamsBuilder.addFilter(" trade_type= 3 ","OR");

        try{

            OpenSearchExecuteResult openSearchExecuteResult = executeOpenSearch(searcherClient, searchParamsBuilder);

            List<OpenSearchFacet> facet = openSearchExecuteResult.getResult().getFacet();
            for (OpenSearchFacet openSearchFacet : facet) {

                long count = 0;
                BigDecimal totalShellProfit = new BigDecimal(0.0);
                String value="";
                List<OpenSearchFacetItem> items = openSearchFacet.getItems();
                for (OpenSearchFacetItem item : items) {
                    if(item.getValue().equals(String.valueOf(TradeTypeInterface.aiStopProfit)) || item.getValue().equals(String.valueOf(TradeTypeInterface.manualClearRepository)))
                    {

                        if(null != item.getCount())
                            count +=Long.parseLong(item.getCount());
                        if(null != item.getSum())
                        {
                            BigDecimal bigDecimal = new BigDecimal(item.getSum());
                            totalShellProfit = totalShellProfit.add(bigDecimal);
                        }
                        if(null != item.getValue())
                            value+=item.getValue();
                    }
                }

                sumShellProfitAggregateVo.setCount(count);
                sumShellProfitAggregateVo.setTotalShellProfit(totalShellProfit.doubleValue());
                sumShellProfitAggregateVo.setValue(value);
            }

            //聚合搜索 追踪盈利
            Aggregate extraProfitAgg = setOpenSearchAggregate(tradeTypeGroupKey, sumTotalExtraProfitAggFun);
            searchParams.addToAggregates(extraProfitAgg);
            SearchParamsBuilder totalExtraProfitBuilder = SearchParamsBuilder.create(searchParams);
            OpenSearchExecuteResult totalExtraProfitResult = executeOpenSearch(searcherClient, totalExtraProfitBuilder);
            facet = totalExtraProfitResult.getResult().getFacet();

            for (OpenSearchFacet openSearchFacet : facet) {
                List<OpenSearchFacetItem> items = openSearchFacet.getItems();
                for (OpenSearchFacetItem item : items) {
                    if(item.getValue().equals(String.valueOf(SHELL_STATUS)))
                    {
                        if(null != item.getSum())
                            sumShellProfitAggregateVo.setTotalTrackProfit(Double.parseDouble(item.getSum()));

                    }
                }

            }

            return sumShellProfitAggregateVo;

        }catch (Exception e) {
            e.printStackTrace();
            return sumShellProfitAggregateVo;
        }

    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(this.tradeOrderMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Integer updateOpenSearchFromSql(int id) {
        return pushToOpenSearch(id)==true?1:0;
    }

    private Config setOpenSearchConfig(int start,int hits) {

        Config config = new Config(Lists.newArrayList(appName));
        config.setSearchFormat(SearchFormat.FULLJSON);
        config.setStart(start);
        config.setHits(hits);
        config.setFetchFields(openSearchFetchField);
        return config;
    }

    private Aggregate setOpenSearchAggregate(String groupKey,String aggFun) {
        Aggregate aggregate = new Aggregate();
        aggregate.setGroupKey(groupKey);
        aggregate.setAggFun(aggFun);
        return  aggregate;
    }

    private OpenSearchExecuteResult executeOpenSearch(SearcherClient searcherClient,SearchParamsBuilder searchParamsBuilder) throws Exception{
        SearchResult execute = searcherClient.execute(searchParamsBuilder);
        String result = execute.getResult();
        System.out.println("result = " + result);
        OpenSearchExecuteResult openSearchExecuteResult = com.alibaba.fastjson.JSONObject.parseObject(result, OpenSearchExecuteResult.class);
        System.out.println("openSearchExecuteResult = " + openSearchExecuteResult);
        return openSearchExecuteResult;
    }
}
