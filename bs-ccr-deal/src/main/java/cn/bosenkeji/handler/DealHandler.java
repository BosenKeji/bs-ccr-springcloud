package cn.bosenkeji.handler;

import cn.bosenkeji.messaging.MySource;
import cn.bosenkeji.vo.RocketMQResult;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
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
import java.util.concurrent.TimeUnit;
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
        String msg = "{\"price\":49.32,\"deep\":[[49.32,7.315],[49.31,50.4387],[49.3,6.2796],[49.27,10.1365],[49.26,39.959],[49.24,19.7702],[49.23,70.7692],[49.22,24.0994],[49.21,31.6065],[49.2,31.1273],[49.19,11.994],[49.17,54.1264],[49.15,66.56],[49.14,59],[49.13,13.563],[49.12,5.3555],[49.1,10],[49.09,5.092],[49.08,141.76],[49.05,47],[49.03,71],[49.02,293.84],[49,177.8083],[48.99,0.0255],[48.98,16.3463],[48.97,0.0448],[48.96,0.0058],[48.95,19.0256],[48.94,0.0118],[48.93,57.5391],[48.92,5.0292],[48.91,0.0892],[48.9,0.0573],[48.89,0.092],[48.88,0.0452],[48.87,0.0489],[48.86,3.9289],[48.85,5.7085],[48.84,0.5615],[48.83,0.0829],[48.82,0.0381],[48.81,0.0344],[48.8,26.5354],[48.79,0.0409],[48.77,0.0386],[48.76,0.067],[48.75,0.0426],[48.74,0.0338],[48.73,5.1438],[48.72,142.7612],[48.71,0.0089],[48.7,78.2136],[48.69,151.81],[48.64,3.0811],[48.62,0.2813],[48.6,2.1832],[48.58,9.1523],[48.56,22.0226],[48.55,2],[48.51,21.4274],[48.5,60.5805],[48.48,47.1415],[48.38,38],[48.33,234.7681],[48.32,0.0194],[48.3,686.5261],[48.24,369.3519],[48.2,162.39],[48.19,0.0114],[48.17,0.4646],[48.16,0.0166],[48.15,0.0119],[48.14,0.0198],[48.1,13.5269],[48.08,0.0286],[48.07,0.0164],[48.05,50],[48.04,0.2813],[48,398.6193],[47.96,7.3603],[47.94,217.6851],[47.92,10.1071],[47.9,7.2971],[47.85,6],[47.8,9.5085],[47.78,10],[47.76,0.1332],[47.75,15],[47.7,43.4326],[47.69,7.5355],[47.68,20],[47.65,5],[47.6,11.2908],[47.59,3],[47.55,191.3599],[47.52,149.31],[47.5,9],[47.45,0.2813],[47.35,31.5568],[47.32,26],[47.3,2.8198],[47.2,2],[47.16,183.4974],[47.13,0.13],[47.1,12.54],[47,55.1289],[46.94,0.1166],[46.87,0.2813],[46.85,37.5021],[46.83,2.78],[46.8,26.0524],[46.77,939.655],[46.6,3],[46.53,4.8103],[46.5,70.01],[46.38,0.2668],[46.35,1.2002],[46.3,0.54],[46.29,0.9601],[46.28,0.2813],[46.08,1.4095],[46,249.9955],[45.85,20],[45.8,5.5],[45.71,30.8998],[45.7,0.2813],[45.61,20],[45.6,3],[45.58,5],[45.55,2],[45.5,52],[45.45,703.5922],[45.28,0.1],[45.23,0.6654],[45.11,0.2813],[45.1,20],[45.08,0.6],[45.07,0.2397],[45.01,0.2],[45,332.5102],[44.67,0.2837],[44.52,0.2813],[44.12,5.7537],[44.1,0.0577],[44,24.1],[43.94,0.2813],[43.88,50],[43.84,10],[43.71,438.0529],[43.36,0.9816]],\"symbol\":\"btsusdt\"}";
        long a = Timestamp.valueOf(LocalDateTime.now()).getTime();
        consumerMessage(msg);
        long b = Timestamp.valueOf(LocalDateTime.now()).getTime();
        return String.valueOf(b-a)+"ms";
    }



//    @StreamListener("input1")
    private void consumerMessage(String msg) {

        //将json字符串转换为json对象
        JSONObject jsonObject = JSON.parseObject(msg);

        //获取实时价格
        Double price = Double.valueOf(jsonObject.get("price").toString());

        //获取深度
        Map<Double,Double> deep = new HashMap<>();


        JSONArray deepArray = (JSONArray) jsonObject.get("deep");
        if (deepArray.get(0) != null) {
            for (int i=0;i<deepArray.size();i++) {
                JSONArray o = (JSONArray) deepArray.get(i);
                deep.put(Double.valueOf(o.get(0).toString()),Double.valueOf(o.get(1).toString()));
            }
        } else {
            return;
        }


        //获取货币对的值
        String symbol = jsonObject.get("symbol").toString();

        //获取所有交易的key
        Set<String> keys = redisTemplate.keys("trade-condition_*");

//        //TODO Test数据
//        Set<String> keys = new HashSet<>();
//        keys.add("aaa");

        //过滤不是该货币对的key
        Set<String> filterSet = keys.stream().filter((s) -> s.indexOf(symbol) != -1).collect(Collectors.toSet());
        //获取key对应的value
        ConcurrentHashMap<String,JSONObject> tradeMap = new ConcurrentHashMap<>();
        filterSet.stream().forEach((s)->{
            JSONObject o = (JSONObject) redisTemplate.opsForValue().get(s);
            tradeMap.put(s,o);
        });



        //TODO 遍历所有的交易trade
        filterSet.parallelStream().forEach((s)->{
//        keys.parallelStream().forEach((s)->{

            //获取该用户redis中的数据
            String redisKey = s;
            JSONObject trade = tradeMap.get(s);

            if (trade == null) {
                return;
            }

            //判断是否需要给node发消息
            Integer canSendMsg2Node = Integer.valueOf(trade.get("canSendMsg2Node").toString());

            if (canSendMsg2Node == -1 ) {
                return;
            }

            //计算实时收益比   判断买卖
            Double positionCost = Double.valueOf(trade.get("position_cost").toString());
//            Double positionCost = 0.0;
            //持仓数量
            Double positionNum = Double.valueOf(trade.get("position_num").toString());
//            Double positionNum = 0.0;
            //实时收益比
            double realTimeEarningRatio = countRealTimeEarningRatio(positionNum,positionCost,price);

            //redis中添加实时收益比
//            updateRedisString(redisKey,"real_time_earning_ratio",realTimeEarningRatio);

            String accessKey = trade.getString("accessKey");
            String secretKey = trade.getString("secretKey");

            if (realTimeEarningRatio >= 1) {
//            if (false) {

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
                Object targetProfitPrice = trade.get("target_profit_price");
                Object is_use_follow_target_profit = trade.get("is_use_follow_target_profit");

                int stopProfitType = Integer.valueOf(is_use_follow_target_profit==null ? "0" : is_use_follow_target_profit.toString());
                double stopProfitPrice = Double.valueOf( targetProfitPrice==null ? "0" : targetProfitPrice.toString());
                double callBackRatio = Double.valueOf(trade.get("turn_down_ratio").toString());
                double stopProfitRatio = Double.valueOf(trade.get("emit_ratio").toString());
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
                int orderNumber = Integer.valueOf(trade.get("finished_order").toString());
                int maxOrderNumber = Integer.valueOf(trade.get("max_trade_order").toString());
                double averagePosition = positionCost/positionNum;
                double buildPositionInterval = Double.valueOf(trade.get("store_split").toString());
                //获取交易量，计算拟买入均价
                double buyVolume = Double.valueOf(trade.getJSONObject("buy_volume").get(orderNumber+"").toString());
                double averagePrice = countAveragePrice(deep,buyVolume);

                Object follow_lower_ratio = trade.get("follow_lower_ratio");
                Object follow_callback_ratio = trade.get("follow_callback_ratio");
                double followLowerRatio = Double.valueOf(follow_lower_ratio == null ? "0.01" : follow_lower_ratio.toString());
                double followCallbackRatio = Double.valueOf(follow_callback_ratio == null ? "0.1" : follow_callback_ratio.toString());

                double minAveragePrice = Double.valueOf(trade.get("min_averagePrice").toString());
                double firstOrderPrice = Double.valueOf(trade.get("first_order_price").toString());
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
    private double countAveragePrice(Map<Double,Double> deep,double quantity) {
        double priceSum = 0;
        double deepSum = 0;

        for (Map.Entry<Double, Double> entry : deep.entrySet()) {
            Double key = entry.getKey();
            Double value = entry.getValue();
            if ( quantity >= value) {
                priceSum += key*value;
                quantity -= value;
                deepSum += value;
            } else {
                priceSum += key*quantity;
                deepSum += quantity;
                quantity = 0;
                break;
            }
        }
        return priceSum/deepSum;
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