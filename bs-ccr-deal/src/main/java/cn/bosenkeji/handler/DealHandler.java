package cn.bosenkeji.handler;

import cn.bosenkeji.messaging.MySource;
import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RocketMQResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@RestController
public class DealHandler {

//    private static final Logger log = LoggerFactory.getLogger(DealHandler.class);

    @Autowired
    private MySource source;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/redis")
    public String setRedis() {
        String s = "{\"symbol\":\"btcusdt\",\"quote_currency\":\"usdt\",\"accessKey\":\"90854b9e-mn8ikls4qg-d8a152e7-cd30e\",\"secretKey\":\"97d74615-f1e7bf4a-756a0261-c1f24\",\"account_id\":8032430,\"userId\":9,\"max_trade_order\":6,\"budget\":100,\"finished_order\":0,\"leverage\":2,\"trade_times\":4,\"policy_series\":[1,2,4,8,16,32],\"buy_volume\":{\"0\":\"0.000400\",\"1\":\"0.000800\",\"2\":\"0.001600\",\"3\":\"0.003200\",\"4\":\"0.006400\",\"5\":\"0.012800\"},\"first_order_price\":10023.94,\"isFollowBuild\":\"0\",\"isNeedRecordMaxRiskBenefitRatio\":\"0\",\"min_averagePrice\":0,\"store_split\":\"1309.2153560303\",\"trade_status\":\"3\",\"history_max_riskBenefitRatio\":\"0\",\"position_average\":\"0\",\"position_cost\":\"0\",\"position_num\":\"0\",\"real_time_earning_ratio\":0,\"stopProfitRatio\":0.05,\"emit_ratio\":0.05,\"turn_down_ratio\":0.02,\"is_rigger_trace_stop_profit\":\"0\",\"target_profit_price\":null}";
        JSONObject jsonObject = JSON.parseObject(s);
        redisTemplate.opsForValue().set("aaa",s);
        return redisTemplate.opsForValue().get("aaa").toString();
    }


    @RequestMapping("/testRedis")
    public String testRedis() {
        Object o = redisTemplate.opsForValue().get("trade-condition_90854b9e-mn8ikls4qg-d8a152e7-cd30e_97d74615-f1e7bf4a-756a0261-c1f24_btcusdt");
        return o.toString();
    }


    @RequestMapping("/handle")
    public String testHandle() {
        String msg = "{\"price\":7.2704,\"deep\":[[7.2705,108.18],[7.2704,0.9558],[7.2683,172.0793],[7.264,9.0809],[7.2639,137.4759],[7.2624,47.77],[7.2623,82.4835],[7.2619,71.6472],[7.259,27.5517],[7.2589,109.57],[7.2585,71.6234],[7.2582,247.7028],[7.2574,79.98],[7.257,120],[7.2564,115.04],[7.2561,7.3516],[7.256,1346.0536],[7.2558,112],[7.2551,481.232],[7.2549,343.8175],[7.2538,143.3317],[7.2513,110.0009],[7.2511,33.02],[7.2506,60],[7.2502,68.1477],[7.2484,214.9941],[7.2466,800],[7.246,699.37],[7.2421,20.6272],[7.2414,33.06],[7.2394,2.6998],[7.2391,56.7629],[7.2381,20.6578],[7.2364,0.2489],[7.2363,0.1936],[7.236,0.25],[7.2359,0.1462],[7.2355,0.4425],[7.2354,0.1916],[7.2351,0.2291],[7.235,0.1956],[7.2348,0.1936],[7.2346,2.2495],[7.2342,174.12],[7.2335,0.5852],[7.2333,3.903],[7.2313,83.7222],[7.2311,20.6578],[7.23,786.0739],[7.2279,33.12],[7.2276,1.2694],[7.2274,0.7208],[7.2272,0.657],[7.2271,1.0079],[7.2269,0.6253],[7.2268,0.7208],[7.2258,0.7144],[7.2257,0.6634],[7.2251,13.8832],[7.2247,59.7],[7.2232,13.9071],[7.2231,20.6808],[7.2226,16.2412],[7.222,422.2618],[7.22,60],[7.2147,20.701],[7.2138,68.3486],[7.2123,0.1959],[7.2102,20.6558],[7.2092,10],[7.2091,2.2495],[7.2075,106.1742],[7.205,34.5124],[7.2027,0.5852],[7.2009,50],[7.2008,2.7526],[7.2,2816.4997],[7.1999,3.903],[7.1998,396.7761],[7.1988,515.096],[7.1955,13.1761],[7.1911,22.8592],[7.19,17.7571],[7.1897,16.2412],[7.1884,116.2011],[7.1853,42.5717],[7.1836,2.2495],[7.18,296.933],[7.1794,0.1959],[7.1772,127.4289],[7.1765,2971.3],[7.1753,48.4402],[7.1751,41.8112],[7.1726,50],[7.1718,0.5852],[7.1717,20.9237],[7.17,10],[7.1666,3.903],[7.1657,129.8686],[7.1652,102],[7.164,0.25],[7.1632,34.5124],[7.1581,2.2495],[7.1569,10.7571],[7.1568,14.6007],[7.1559,145.2639],[7.1517,13.8832],[7.15,130.9466],[7.1464,0.1959],[7.1463,20],[7.141,0.5852],[7.1359,51.4583],[7.1352,3.15],[7.1333,3.903],[7.1326,2.2495],[7.1271,1527.1],[7.1257,0.1858],[7.1255,0.1696],[7.1253,0.5221],[7.1249,0.1842],[7.1247,0.181],[7.1238,16.2412],[7.1237,10.7571],[7.1215,34.5124],[7.12,262.9019],[7.1151,0.1527],[7.115,13.8832],[7.1149,0.1556],[7.1146,0.1527],[7.1141,0.1411],[7.1135,0.3661],[7.1133,0.1847],[7.1111,686.3704],[7.1101,0.5852],[7.1071,2.2495],[7.1012,1376.9026],[7.1,4004.9836],[7.0999,3.903],[7.092,0.25],[7.0909,16.2412],[7.0906,10.7571],[7.0816,2.2495],[7.0805,0.1959],[7.0797,34.5124],[7.0796,20],[7.0793,0.5852],[7.0783,13.8832],[7.071,40],[7.07,151.5855],[7.0668,1524.6]],\"symbol\":\"btsusdt\"}";
        long a = Timestamp.valueOf(LocalDateTime.now()).getTime();
        consumerMessage(msg);
        long b = Timestamp.valueOf(LocalDateTime.now()).getTime();
        return String.valueOf(b-a)+"ms";
    }



    @StreamListener("input1")
    private void consumerMessage(String msg) {

        //将json字符串转换为json对象
        JSONObject jsonObject = JSON.parseObject(msg);

        //获取实时价格
        Double price = Double.valueOf(jsonObject.get("price").toString());

        //获取深度
        Map<Double,Double> deep;

        JSONArray deepArray = (JSONArray) jsonObject.get("deep");

        if (deepArray.get(0) != null) {
            deep = DealParameterParser.convertJsonArrayToMap(deepArray);
        } else {
            return;
        }

        //获取货币对的值
        String symbol = jsonObject.get("symbol").toString();

        //获取所有交易的key
        Set<String> keys = redisTemplate.keys("trade-condition_*");


        //过滤不是该货币对的key
        Set<String> filterSet = keys.stream().filter((s) -> s.indexOf(symbol) != -1).collect(Collectors.toSet());
        //获取key对应的value
        ConcurrentHashMap<String,JSONObject> tradeMap = new ConcurrentHashMap<>();
        filterSet.stream().forEach((s)->{
            JSONObject o = (JSONObject) redisTemplate.opsForValue().get(s);
            tradeMap.put(s,o);
        });



        //TODO 遍历所有的交易trade
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
//            Double positionCost = 0.0;
            //持仓数量
            Double positionNum = dealParameter.getPositionNum();
//            Double positionNum = 0.0;
            //实时收益比
            double realTimeEarningRatio = countRealTimeEarningRatio(positionNum,positionCost,price);

            //TODO redis中添加实时收益比
//            updateRedisString(redisKey,"real_time_earning_ratio",realTimeEarningRatio);

            String accessKey = dealParameter.getAccessKey();
            String secretKey = dealParameter.getSecretKey();

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

                int stopProfitType = dealParameter.getIsUseFollowTargetProfit();
                double stopProfitPrice = dealParameter.getTargetProfitPrice();
                double callBackRatio = dealParameter.getTurnDownRatio();
                double stopProfitRatio = dealParameter.getEmitRatio();
                boolean isSell = isSell(positionCost, stopProfitType, stopProfitPrice, stopProfitRatio,
                        realTimeEarningRatio, stopProfitRatio, callBackRatio,redisKey);
                if (isSell) {
                    //mq发送卖的消息
                    boolean sendSell = sendMessage(accessKey,secretKey,symbol,"sell");
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
                int orderNumber = dealParameter.getFinishedOrder();
                int maxOrderNumber = dealParameter.getMaxTradeOrder();
                double averagePosition = positionCost/positionNum;
                double buildPositionInterval = dealParameter.getStoreSplit();
                //获取交易量，计算拟买入均价
                Map buyVolume = dealParameter.getBuyVolume();
                double averagePrice = countAveragePrice(deep,buyVolume);


                double followLowerRatio = dealParameter.getFollowLowerRatio() == 0.0 ? 0.01 : dealParameter.getFollowLowerRatio();
                double followCallbackRatio = dealParameter.getFollowCallbackRatio() == 0.0 ? 0.1 : dealParameter.getFollowCallbackRatio();

                double minAveragePrice = dealParameter.getMinAveragePrice();
                double firstOrderPrice = dealParameter.getFirstOrderPrice();
                boolean isBuy = isBuy( orderNumber, maxOrderNumber, averagePosition,
                        buildPositionInterval, averagePrice,followLowerRatio,followCallbackRatio
                        ,minAveragePrice,firstOrderPrice,redisKey);

                if (isBuy) {
                    //mq发送买的消息
                     boolean sendBuy = sendMessage(accessKey,secretKey,symbol,"buy");
                }
            }
        });


    }


    /**
     * 计算实时收益比  买价*持仓数量/持仓费用  精确小数点后4位
     * @param number 持仓数量
     * @param cost 持仓费用
     * @param realTimePrice 实时买价
     * @return 实时收益比
     */
    private double countRealTimeEarningRatio(double number, double cost, double realTimePrice) {
        return realTimePrice*number/cost;
    }

    /**
     * 计算拟买入均价
     * @param deep 实时深度
     * @param quantity 某交易量
     * @return 拟买入均价
     */
    //TODO
    private double countAveragePrice(Map<Double,Double> deep,Map<Double, Double> quantity) {
//        double priceSum = 0;
//        double deepSum = 0;
//
//        for (Map.Entry<Double, Double> entry : deep.entrySet()) {
//            Double key = entry.getKey();
//            Double value = entry.getValue();
//            if ( quantity >= value) {
//                priceSum += key*value;
//                quantity -= value;
//                deepSum += value;
//            } else {
//                priceSum += key*quantity;
//                deepSum += quantity;
//                quantity = 0;
//                break;
//            }
//        }
        return 0.0;
    }


    /**
     * 是否买的逻辑
     * @param orderNumber 已做单数 redis获取
     * @param maxOrderNumber 最大交易单数 redis获取
     * @param averagePosition 持仓均价 调用上面的公式
     * @param buildPositionInterval 建仓间隔 redis获取
     * @param averagePrice 拟买入均价 调用上面公式
     * @param followLowerRatio 追踪下调比
     * @param followCallbackRatio 追踪回调比
     * @param firstOrderPrice 开始策略的现价
     * @param redisKey 获取redis中的值
     * @return 是否买
     */

    private boolean isBuy(int orderNumber, int maxOrderNumber,
                         double averagePosition, double buildPositionInterval,double averagePrice,
                         double followLowerRatio,double followCallbackRatio,double minAveragePrice,
                         double firstOrderPrice,String redisKey
                         ) {
        //是否需要判断？ 达到最大交易单数？
        if ( orderNumber == maxOrderNumber ) {
            return false;
        }
        //是否为第一单？ 第一单直接购买
        if ( orderNumber == 0 ) {
            return true;
        }
        //设置策略时现价是否小于等于整体持仓均价-建仓间隔*最大建仓数减1？
        if ( firstOrderPrice > (buildPositionInterval*(maxOrderNumber-1)) ) {
            return false;
        }

        //计算拟买入均价 参数传入

        //追踪下调比
        //获取下调均价 下调均价=(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
        double lowerAveragePrice = (averagePosition - buildPositionInterval) - (averagePosition*followLowerRatio);

        //拟买入均价小于等于下调均价？ 触发追踪建仓
        if (averagePrice > lowerAveragePrice) {
            //标志已触发追踪建仓
            updateRedisString(redisKey,"isFollowBuild",1);
            return false;
        }

        //计算回调均价 回调均价=最小均价+整体持仓均价*追踪回调比
        double callbackAveragePrice = minAveragePrice + averagePosition*followCallbackRatio;

        //记录最小拟买入均价
        if (minAveragePrice == 0 || minAveragePrice > averagePrice) {
            updateRedisString(redisKey,"min_averagePrice",averagePrice);
        }


        //拟买入均价是否大于等于回调均价？ 是则确定买入\
        return (averagePrice >= callbackAveragePrice);
    }

    /**
     *  确定卖的逻辑
     * @param positionPrice 持仓费用
     * @param stopProfitType 止盈方式
     * @param stopProfitPrice 止盈金额
     * @param stopProfitRatio 止盈比例
     * @param realTimeEarningRatio 实时收益比
     * @param triggerRatio 触发比例
     * @param callBackRatio 回调比例
     * @param redisKey 查询redis用的key
     * @return 是否卖
     */

    private boolean isSell(double positionPrice, int stopProfitType, double stopProfitPrice
            ,double stopProfitRatio ,double realTimeEarningRatio,double triggerRatio, double callBackRatio,String redisKey) {
        //读取止盈金额和止盈比例，两种止盈方式，达到一种即可 参数传入
        //判断是否启用追踪止盈 if-else  1为追踪止盈，2为固定止盈
        boolean isStopProfitTrace = stopProfitType==1 ;
        //判断是否开启金额止盈
        boolean isStopProfitPrice = stopProfitPrice!=1 ;


        if (isStopProfitTrace) {
            //追踪止盈逻辑
            //收益比≥1+触发比例？ 追踪止盈
            if (realTimeEarningRatio >= (1 + triggerRatio)) {
            //if (true) {

                //获取Redis的值
                JSONObject jsonObject = JSON.parseObject(redisTemplate.opsForValue().get(redisKey).toString());

                //标志进入追踪止盈
                Integer isTriggerTraceStopProfit = Integer.valueOf(jsonObject.get("is_trigger_trace_stop_profit").toString());
                if (isTriggerTraceStopProfit == 0) {
                    updateRedisString(redisKey,"is_trigger_trace_stop_profit",1);
                }
                //记录实时收益比的最高数值
                double maxEarningRation = Double.valueOf(jsonObject.get("history_max_riskBenefitRatio").toString());
                if (maxEarningRation == 0 || maxEarningRation < realTimeEarningRatio) {
                    updateRedisString(redisKey,"history_max_riskBenefitRatio",realTimeEarningRatio);
                }
                //实时收益比≤最高实时收益比-回降比例？ 确定卖出
                if (realTimeEarningRatio <= (maxEarningRation-callBackRatio)) {
                    return true;
                }
            }
        } else {
            //收益比≥1+止盈比例？ //确定卖出
            if (realTimeEarningRatio > (1 + stopProfitRatio)) {
                return true;
            }

        }
        //金额止盈
        return (isStopProfitPrice && (positionPrice * (realTimeEarningRatio-1)) >= stopProfitPrice);

    }

    private void updateRedisString(String redisKey,String valueKey, Double newValue) {
        JSONObject jsonObject = JSON.parseObject(redisTemplate.opsForValue().get(redisKey).toString());
        if (newValue.isNaN()) {
            newValue = 0.0;
        }
        String replace = jsonObject.replace(valueKey, newValue.toString()).toString();
        redisTemplate.opsForValue().set(redisKey,jsonObject.toJSONString());
    }

    private void updateRedisString(String redisKey,String valueKey, Integer newValue) {
        JSONObject jsonObject = JSON.parseObject(redisTemplate.opsForValue().get(redisKey).toString());
        String replace = jsonObject.replace(valueKey, newValue.toString()).toString();
        redisTemplate.opsForValue().set(redisKey,jsonObject.toJSONString());
    }


    private boolean sendMessage(String accessKey,String secretKey,String symbol,String type) {
        RocketMQResult rocketMQResult = new RocketMQResult();
        rocketMQResult.setAccessKey(accessKey);
        rocketMQResult.setSecretKey(secretKey);
        rocketMQResult.setSymbol(symbol);
        rocketMQResult.setType(type);
        JSONObject jsonResult = (JSONObject) JSONObject.toJSON(rocketMQResult);
        Message<String> jsonMessage = MessageBuilder.withPayload(jsonResult.toJSONString()).build();
        return source.output1().send(jsonMessage);
    }

}