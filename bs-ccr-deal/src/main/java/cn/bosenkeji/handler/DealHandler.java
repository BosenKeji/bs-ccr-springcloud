package cn.bosenkeji.handler;

import cn.bosenkeji.messaging.MySource;
import cn.bosenkeji.utils.DealCalculator;
import cn.bosenkeji.utils.DealParameterParser;
import cn.bosenkeji.utils.DealUtil;
import cn.bosenkeji.utils.RealTimeTradeParameterParser;
import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RealTimeTradeParameter;
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


//    @GetMapping("/redis")
//    public String setRedis() {
//        String s = "{\"symbol\":\"btcusdt\",\"quote_currency\":\"usdt\",\"accessKey\":\"90854b9e-mn8ikls4qg-d8a152e7-cd30e\",\"secretKey\":\"97d74615-f1e7bf4a-756a0261-c1f24\",\"account_id\":8032430,\"userId\":9,\"max_trade_order\":6,\"budget\":100,\"finished_order\":0,\"leverage\":2,\"trade_times\":4,\"policy_series\":[1,2,4,8,16,32],\"buy_volume\":{\"0\":\"0.000400\",\"1\":\"0.000800\",\"2\":\"0.001600\",\"3\":\"0.003200\",\"4\":\"0.006400\",\"5\":\"0.012800\"},\"first_order_price\":10023.94,\"isFollowBuild\":\"0\",\"isNeedRecordMaxRiskBenefitRatio\":\"0\",\"min_averagePrice\":0,\"store_split\":\"1309.2153560303\",\"trade_status\":\"3\",\"history_max_riskBenefitRatio\":\"0\",\"position_average\":\"0\",\"position_cost\":\"0\",\"position_num\":\"0\",\"real_time_earning_ratio\":0,\"stopProfitRatio\":0.05,\"emit_ratio\":0.05,\"turn_down_ratio\":0.02,\"is_rigger_trace_stop_profit\":\"0\",\"target_profit_price\":null}";
//        JSONObject jsonObject = JSON.parseObject(s);
//        redisTemplate.opsForValue().set("aaa",s);
//        return redisTemplate.opsForValue().get("aaa").toString();
//    }
//
//
//    @RequestMapping("/testRedis")
//    public String testRedis() {
//        Object o = redisTemplate.opsForValue().get("trade-condition_90854b9e-mn8ikls4qg-d8a152e7-cd30e_97d74615-f1e7bf4a-756a0261-c1f24_btcusdt");
//        return o.toString();
//    }
//
//
    @RequestMapping("/handle")
    public void testHandle() {
        String msg = "{\"price\":7.2704,\"deep\":[[7.2705,108.18],[7.2704,0.9558],[7.2683,172.0793],[7.264,9.0809],[7.2639,137.4759],[7.2624,47.77],[7.2623,82.4835],[7.2619,71.6472],[7.259,27.5517],[7.2589,109.57],[7.2585,71.6234],[7.2582,247.7028],[7.2574,79.98],[7.257,120],[7.2564,115.04],[7.2561,7.3516],[7.256,1346.0536],[7.2558,112],[7.2551,481.232],[7.2549,343.8175],[7.2538,143.3317],[7.2513,110.0009],[7.2511,33.02],[7.2506,60],[7.2502,68.1477],[7.2484,214.9941],[7.2466,800],[7.246,699.37],[7.2421,20.6272],[7.2414,33.06],[7.2394,2.6998],[7.2391,56.7629],[7.2381,20.6578],[7.2364,0.2489],[7.2363,0.1936],[7.236,0.25],[7.2359,0.1462],[7.2355,0.4425],[7.2354,0.1916],[7.2351,0.2291],[7.235,0.1956],[7.2348,0.1936],[7.2346,2.2495],[7.2342,174.12],[7.2335,0.5852],[7.2333,3.903],[7.2313,83.7222],[7.2311,20.6578],[7.23,786.0739],[7.2279,33.12],[7.2276,1.2694],[7.2274,0.7208],[7.2272,0.657],[7.2271,1.0079],[7.2269,0.6253],[7.2268,0.7208],[7.2258,0.7144],[7.2257,0.6634],[7.2251,13.8832],[7.2247,59.7],[7.2232,13.9071],[7.2231,20.6808],[7.2226,16.2412],[7.222,422.2618],[7.22,60],[7.2147,20.701],[7.2138,68.3486],[7.2123,0.1959],[7.2102,20.6558],[7.2092,10],[7.2091,2.2495],[7.2075,106.1742],[7.205,34.5124],[7.2027,0.5852],[7.2009,50],[7.2008,2.7526],[7.2,2816.4997],[7.1999,3.903],[7.1998,396.7761],[7.1988,515.096],[7.1955,13.1761],[7.1911,22.8592],[7.19,17.7571],[7.1897,16.2412],[7.1884,116.2011],[7.1853,42.5717],[7.1836,2.2495],[7.18,296.933],[7.1794,0.1959],[7.1772,127.4289],[7.1765,2971.3],[7.1753,48.4402],[7.1751,41.8112],[7.1726,50],[7.1718,0.5852],[7.1717,20.9237],[7.17,10],[7.1666,3.903],[7.1657,129.8686],[7.1652,102],[7.164,0.25],[7.1632,34.5124],[7.1581,2.2495],[7.1569,10.7571],[7.1568,14.6007],[7.1559,145.2639],[7.1517,13.8832],[7.15,130.9466],[7.1464,0.1959],[7.1463,20],[7.141,0.5852],[7.1359,51.4583],[7.1352,3.15],[7.1333,3.903],[7.1326,2.2495],[7.1271,1527.1],[7.1257,0.1858],[7.1255,0.1696],[7.1253,0.5221],[7.1249,0.1842],[7.1247,0.181],[7.1238,16.2412],[7.1237,10.7571],[7.1215,34.5124],[7.12,262.9019],[7.1151,0.1527],[7.115,13.8832],[7.1149,0.1556],[7.1146,0.1527],[7.1141,0.1411],[7.1135,0.3661],[7.1133,0.1847],[7.1111,686.3704],[7.1101,0.5852],[7.1071,2.2495],[7.1012,1376.9026],[7.1,4004.9836],[7.0999,3.903],[7.092,0.25],[7.0909,16.2412],[7.0906,10.7571],[7.0816,2.2495],[7.0805,0.1959],[7.0797,34.5124],[7.0796,20],[7.0793,0.5852],[7.0783,13.8832],[7.071,40],[7.07,151.5855],[7.0668,1524.6]],\"symbol\":\"btsusdt\"}";
        consumerMessage(msg);
    }



//    @StreamListener("input1")
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
            JSONObject o = (JSONObject) redisTemplate.opsForValue().get(s);
            if (o != null) tradeMap.put(s,o);
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

            if (realTimeEarningRatio >= 1) {
//            if (true) {

            //判断是否卖
                /*
                    获取判断买的参数
                    double positionPrice, 上面的positionCost
                    int stopProfitType,
                    double stopProfitPrice,
                    double stopProfitRatio, 和止盈触发比一致
                    double realTimeEarningRatio, 上面
                    double triggerRatio, 和上面的stopProfitRatio
                    double callBackRatio
                 */

//                boolean isSell = isSell(positionCost, stopProfitType, stopProfitPrice, stopProfitRatio,
//                        realTimeEarningRatio, stopProfitRatio, callBackRatio,redisKey);
                boolean isSell = DealCalculator.isSell(dealParameter,realTimeTradeParameter,redisTemplate);
                if (isSell) {
                    //mq发送卖的消息
                    boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_SELL,source);
                    log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL+"  消息发送："+isSend);
                }

            } else {
                //判断是否买
                /*
                    获取判断买的参数
                    double realTimePrice, 上面的price
                    int orderNumber,
                    int maxOrderNumber,
                    double averagePosition,
                    double buildPositionInterval,
                    double averagePrice,
                    double followLowerRatio,
                    double followCallbackRatio,
                    double minAveragePrice
                    double firstOrderPrice

                 */
//                int orderNumber = dealParameter.getFinishedOrder();
//                int maxOrderNumber = dealParameter.getMaxTradeOrder();
//                double averagePosition = positionCost/positionNum;
//                double buildPositionInterval = dealParameter.getStoreSplit();
//                //获取交易量，计算拟买入均价
//                Map buyVolume = dealParameter.getBuyVolume();
//                double averagePrice = DealCalculator.countAveragePrice(deep,buyVolume);
//
//
//                double followLowerRatio = dealParameter.getFollowLowerRatio() == 0.0 ? 0.01 : dealParameter.getFollowLowerRatio();
//                double followCallbackRatio = dealParameter.getFollowCallbackRatio() == 0.0 ? 0.1 : dealParameter.getFollowCallbackRatio();
//
//                double minAveragePrice = dealParameter.getMinAveragePrice();
//                double firstOrderPrice = dealParameter.getFirstOrderPrice();
//                boolean isBuy = DealCalculator.isBuy( orderNumber, maxOrderNumber, averagePosition,
//                        buildPositionInterval, averagePrice,followLowerRatio,followCallbackRatio
//                        ,minAveragePrice,firstOrderPrice,redisKey);

                boolean isBuy = DealCalculator.isBuy(dealParameter,realTimeTradeParameter,redisTemplate);

                if (isBuy) {
                    //mq发送买的消息
                     boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_BUY,source);
                     log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY+"  消息发送："+isSend);
                }
            }
        });


    }

}