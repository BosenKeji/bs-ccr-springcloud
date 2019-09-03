package cn.bosenkeji.handler;

import cn.bosenkeji.messaging.MySource;
import cn.bosenkeji.utils.DealCalculator;
import cn.bosenkeji.utils.DealParameterParser;
import cn.bosenkeji.utils.DealUtil;
import cn.bosenkeji.utils.RealTimeTradeParameterParser;
import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RealTimeTradeParameter;
import cn.bosenkeji.vo.RedisParameter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@RestController
public class DealHandler {

    private static final Logger log = LoggerFactory.getLogger(DealHandler.class);

    @Autowired
    private MySource source;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/redis")
    public String setRedis() {
        String s = "{\n" +
                "\t\"symbol\": \"xrpusdt\",\n" +
                "\t\"leverage\": 4,\n" +
                "\t\"updateDate\": \"2019-09-02 12:14:37\",\n" +
                "\t\"emit_ratio\": 0.05,\n" +
                "\t\"store_split\": \"0.021709610309502\",\n" +
                "\t\"position_average\": 0.25439,\n" +
                "\t\"follow_lower_ratio\": \"0.01\",\n" +
                "\t\"min_order_value\": 1,\n" +
                "\t\"max_trade_order\": 6,\n" +
                "\t\"trade_times\": 8,\n" +
                "\t\"canSendMsg2Node\": 1,\n" +
                "\t\"trade_status\": \"1\",\n" +
                "\t\"real_time_earning_ratio\": 0,\n" +
                "\t\"symbol_id\": \"92\",\n" +
                "\t\"buy_volume\": {\n" +
                "\t\t\"0\": \"8.00\",\n" +
                "\t\t\"1\": \"16.00\",\n" +
                "\t\t\"2\": \"32.00\",\n" +
                "\t\t\"3\": \"64.00\",\n" +
                "\t\t\"4\": \"128.00\",\n" +
                "\t\t\"5\": \"256.00\"\n" +
                "\t},\n" +
                "\t\"first_order_price\": 0.25431,\n" +
                "\t\"createDate\": \"2019-09-02 12:12:50\",\n" +
                "\t\"budget\": 25,\n" +
                "\t\"secretKey\": \"97d74615-f1e7bf4a-756a0261-c1f24\",\n" +
                "\t\"isNeedRecordMaxRiskBenefitRatio\": \"0\",\n" +
                "\t\"min_averagePrice\": 0.24872,\n" +
                "\t\"position_cost\": 2.03512,\n" +
                "\t\"value_precision\": 8,\n" +
                "\t\"turn_down_ratio\": 0.02,\n" +
                "\t\"coinPairChoiceId\": 92,\n" +
                "\t\"userId\": \"9\",\n" +
                "\t\"history_max_riskBenefitRatio\": \"0\",\n" +
                "\t\"quote_currency\": \"usdt\",\n" +
                "\t\"price_precision\": 5,\n" +
                "\t\"policy_series\": [1, 2, 4, 8, 16, 32],\n" +
                "\t\"min_order_amt\": 1,\n" +
                "\t\"follow_callback_ratio\": \"0.01\",\n" +
                "\t\"account_id\": \"8032430\",\n" +
                "\t\"accessKey\": \"90854b9e-mn8ikls4qg-d8a152e7-cd30e\",\n" +
                "\t\"isFollowBuild\": 1,\n" +
                "\t\"amount_precision\": 2,\n" +
                "\t\"finished_order\": 1,\n" +
                "\t\"position_num\": 8,\n" +
                "\t\"stopProfitRatio\": 0.05\n" +
                "}";
        Object aaa = redisTemplate.opsForList().leftPop("aaa");
        JSONObject jsonObject1 = JSONObject.parseObject(aaa.toString());
        return jsonObject1.toJSONString();
    }


    @StreamListener("input1")
    private void consumerMessage(String msg) {

        //将json字符串转换为json对象
        JSONObject jsonObject = JSON.parseObject(msg);

        //获取实时价格
        RealTimeTradeParameter realTimeTradeParameter = new RealTimeTradeParameterParser(jsonObject).getRealTimeTradeParameter();
        Double price = realTimeTradeParameter.getPrice();
        Map<Double, Double> deep = realTimeTradeParameter.getDeep();
        String symbol = realTimeTradeParameter.getSymbol();

        //mq参数不正确
        if (price == null || CollectionUtils.isEmpty(deep) || symbol == null) {
            return;
        }
        //获取所有交易的key
        Set<String> keys = redisTemplate.keys(DealUtil.TRADE_KEYS_PATTERN);

        //过滤不是该货币对的key
        if (CollectionUtils.isEmpty(keys)) { return; }

        Set<String> filterSet = keys.stream().filter((s) -> s.contains(symbol)).collect(Collectors.toSet());
        //获取key对应的value
        ConcurrentHashMap<String,JSONObject> tradeMap = new ConcurrentHashMap<>();
        filterSet.forEach((s)->{
            Object result = redisTemplate.opsForValue().get(s);
            if (result instanceof String) tradeMap.put(s,JSONObject.parseObject((String) result));
            if (result instanceof JSONObject) tradeMap.put(s,(JSONObject) result);
            if (result instanceof HashMap) tradeMap.put(s,JSONObject.parseObject(JSON.toJSONString(result)));
        });



        //遍历所有的交易trade
        filterSet.parallelStream().forEach((redisKey)->{
//        keys.parallelStream().forEach((s)->{

            //获取该用户redis中的数据
            JSONObject trade = tradeMap.get(redisKey);

            if (trade == null) {
                return;
            }

            DealParameter dealParameter = new DealParameterParser(trade,redisKey).getDealParameter();

            //初始化或获取 java要操作redis的key和value
            RedisParameter redisParameter = DealUtil.javaRedisParameter(dealParameter, redisTemplate);

            //判断是否需要给node发消息
            Integer canSendMsg2Node = dealParameter.getCanSendMsgToNode();

            if (canSendMsg2Node == 0 ) {
                return;
            }

            //计算实时收益比   判断买卖
            Double positionCost = dealParameter.getPositionCost();
            //持仓数量
            Double positionNum = dealParameter.getPositionNum();
            //实时收益比
            if (positionCost == 0.0) {
                positionCost = 1.0;
            }
            double realTimeEarningRatio = DealCalculator.countRealTimeEarningRatio(positionNum,positionCost,price);

            //记录实时收益比
            redisParameter.getJsonObject().put(DealUtil.REAL_TIME_EARNING_RATIO,realTimeEarningRatio);
            redisTemplate.opsForValue().set(redisParameter.getRedisKey(),redisParameter.getJsonObject().toJSONString());

            if (realTimeEarningRatio >= 1) {
//            if (true) {
            //判断是否卖
                boolean isSell = DealCalculator.isSell(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);
                if (isSell) {
                    //mq发送卖的消息
                    boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_SELL,source);
                    log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL
                            +"  消息发送："+isSend + "  finished_order:" + dealParameter.getFinishedOrder());
                }

            }

            //判断买
            boolean isBuy = DealCalculator.isBuy(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);

            if (isBuy) {
                //mq发送买的消息
                 boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_BUY,source);
                 log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY
                         +"  消息发送："+isSend + "  finished_order:" + dealParameter.getFinishedOrder());
            }
        });

    }

}