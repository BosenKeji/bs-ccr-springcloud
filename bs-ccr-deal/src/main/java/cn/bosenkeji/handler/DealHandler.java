package cn.bosenkeji.handler;

import cn.bosenkeji.messaging.MySource;
import cn.bosenkeji.vo.RocketMQResult;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@RestController
public class DealHandler {

    @Autowired
    private MySource source;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/redis")
    public String setRedis() {
        String s = "{\"symbol\":\"btsusdt\",\"accessKey\":\"90854b9e-mn8ikls4qg-d8a152e7-cd30e\",\"secretKey\":\"97d74615-f1e7bf4a-756a0261-c1f24\",\"account_id\":8032430,\"max_trade_order\":6,\"budget\":10,\"finished_order\":0,\"leverage\":2,\"trade_times\":101,\"policy_series\":[1,2,4,8,16,32],\"buy_volume\":{\"0\":\"10.10\",\"1\":\"20.20\",\"2\":\"40.40\",\"3\":\"80.80\",\"4\":\"161.60\",\"5\":\"323.20\"},\"first_order_price\":0.0457,\"isFollowBuild\":\"0\",\"isNeedRecordMaxRiskBenefitRatio\":\"0\",\"min_averagePrice\":0,\"store_split\":\"0.0051765\",\"trade_status\":\"0\",\"history_max_riskBenefitRatio\":\"0\",\"position_average\":\"0\",\"position_cost\":\"0\",\"position_num\":\"0\",\"real_time_earning_ratio\":0,\"emit_ratio\":\"0.2\",\"turn_down_ratio\":\"0.1\",\"follow_lower_ratio\":\"0.01\",\"follow_callback_ratio\":\"0.1\",\"is_use_follow_target_profit\":\"1\",\"target_profit_price\":\"50\"}";
        JSONObject jsonObject = JSON.parseObject(s);
        redisTemplate.opsForValue().set("90854b9e-mn8ikls4qg-d8a152e7-cd30e_97d74615-f1e7bf4a-756a0261-c1f24_btsusdt",s);
        return "success";
    }


    @RequestMapping("/handle")
    public String testHandle() {
        String msg =    "{\n" +
                        " \"price\": 2,\n" +
                        " \"deep\": [\n" +
                        "  [7964, 0.0678],\n" +
                        "  [7963, 0.9162],\n" +
                        "  [7961, 0.1],\n" +
                        "  [7960, 12.8898],\n" +
                        "  [7958, 1.2]\n" +
                        " ],\n" +
                        " \"symbol\": \"btsusdt\"\n" +
                        "}";
        consumerMessage(msg);
        return "test";
    }

//    /**
//     *  从redis中取数据填充自选币List
//     * @Author CAJR
//     * @return
//     */
//    private List<CoinPairChoice> fillCoinPairChoiceList(){
//        List<CoinPairChoice> coinPairChoices = new ArrayList<>();
//
//        //获取redis中的所有交易情况的Key
//        Set<String> tradeConditionKeys = redisTemplate.keys("trade_condition_*");
//
//        if (tradeConditionKeys != null){
//            for (String tradeConditionKey : tradeConditionKeys){
//                CoinPairChoice coinPairChoice = new CoinPairChoice();
//                CoinPair coinPair = new CoinPair();
//                JSONObject tradeConditionValueJson = (JSONObject) redisTemplate.opsForValue().get(tradeConditionKey);
//
//                String coinPairName = tradeConditionValueJson.getString("symbol");
//                int userId = Integer.parseInt(tradeConditionValueJson.getString("userId"));
//                coinPairChoice.setUserId(userId);
//                coinPair.setName(coinPairName);
//                coinPairChoice.setCoinPair(coinPair);
//
//                System.out.println(coinPairChoice.toString());
//                coinPairChoices.add(coinPairChoice);
//            }
//        }
//
//        return coinPairChoices;
//    }

//    /**
//     *  从redis中取数据填充用户交易平台Api List
//     * @param
//     */
//    private List<TradePlatformApi> fillTradePlatformApiList(){
//        List<TradePlatformApi> tradePlatformApis = new ArrayList<>();
//
//        //获取redis中的所有交易情况的Key
//        Set<String> tradeConditionKeys = redisTemplate.keys("trade_condition_*");
//        if (tradeConditionKeys != null){
//            for (String tradeConditionKey : tradeConditionKeys){
//                TradePlatformApi tradePlatformApi = new TradePlatformApi();
//                JSONObject tradeConditionValueJson = (JSONObject) redisTemplate.opsForValue().get(tradeConditionKey);
//                int userId = Integer.parseInt(tradeConditionValueJson.getString("userId"));
//                String accessKey = tradeConditionValueJson.getString("accessKey");
//                String secretKey = tradeConditionValueJson.getString("secretKey");
//
//                tradePlatformApi.setUserId(userId);
//                tradePlatformApi.setAccessKey(accessKey);
//                tradePlatformApi.setSecretKey(secretKey);
//
//                tradePlatformApis.add(tradePlatformApi);
//            }
//        }
//
//        return tradePlatformApis;
//    }

    @StreamListener("input1")
    private void consumerMessage(String msg) {
//        System.out.println("consumerMessage--input1->"+msg);

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
        //TODO 过滤不是该货币对的key
        Set<String> filterSet = keys.stream().filter((s) -> s.indexOf(symbol) != -1).collect(Collectors.toSet());
        //获取key对应的value
        ConcurrentHashMap<String,JSONObject> tradeMap = new ConcurrentHashMap<>();
        filterSet.stream().forEach((s)->{
            JSONObject o = (JSONObject) redisTemplate.opsForValue().get(s);
            tradeMap.put(s,o);
        });

        System.out.println("过滤后的KeySet---->"+filterSet);

        System.out.println("tradeMap----------->"+tradeMap);
//        String str = "{\n" +
//                "\t\"symbol\": \"btsusdt\",\n" +
//                "\t\"accessKey\": \"90854b9e-mn8ikls4qg-d8a152e7-cd30e\",\n" +
//                "\t\"secretKey\": \"97d74615-f1e7bf4a-756a0261-c1f24\",\n" +
//                "\t\"account_id\": 8032430,\n" +
//                "\t\"max_trade_order\": 6,\n" +
//                "\t\"budget\": 10,\n" +
//                "\t\"finished_order\": 0,\n" +
//                "\t\"leverage\": 2,\n" +
//                "\t\"trade_times\": 101,\n" +
//                "\t\"policy_series\": [1, 2, 4, 8, 16, 32],\n" +
//                "\t\"buy_volume\": {\n" +
//                "\t\t\"0\": \"10.10\",\n" +
//                "\t\t\"1\": \"20.20\",\n" +
//                "\t\t\"2\": \"40.40\",\n" +
//                "\t\t\"3\": \"80.80\",\n" +
//                "\t\t\"4\": \"161.60\",\n" +
//                "\t\t\"5\": \"323.20\"\n" +
//                "\t},\n" +
//                "\t\"first_order_price\": 0.0457,\n" +
//                "\t\"isFollowBuild\": \"0\",\n" +
//                "\t\"isNeedRecordMaxRiskBenefitRatio\": \"0\",\n" +
//                "\t\"min_averagePrice\": 0,\n" +
//                "\t\"store_split\": \"0.0051765\",\n" +
//                "\t\"trade_status\": \"0\",\n" +
//                "\t\"history_max_riskBenefitRatio\": \"0\",\n" +
//                "\t\"position_average\": \"0\",\n" +
//                "\t\"position_cost\": \"0\",\n" +
//                "\t\"position_num\": \"0\",\n" +
//                "\t\"real_time_earning_ratio\": 0,\n" +
//                "\t\"emit_ratio\": \"0.2\",\n" +
//                "\t\"turn_down_ratio\": \"0.1\",\n" +
//                "\t\"follow_lower_ratio\": \"0.01\",\n" +
//                "\t\"follow_callback_ratio\": \"0.1\",\n" +
//                "\t\"is_use_follow_target_profit\": \"1\",\n" +
//                "\t\"target_profit_price\": \"50\"\n" +
//                "}";
//        JSONObject object = JSONObject.parseObject(str);
//        ConcurrentHashMap<String,JSONObject> tradeMap = new ConcurrentHashMap<>();
//        tradeMap.put("aaa",object);


        //获取 自选货币对信息
//        List<CoinPairChoice> coinPairChoiceList = coinPairChoiceClientService.findAll();
//        List<CoinPairChoice> coinPairChoiceList = fillCoinPairChoiceList();


        //过滤暂停和停止、不是该货币对的信息
//        List<CoinPairChoice> filterList = coinPairChoiceList.stream()
//                .filter((e) -> (e.getIsStart() == 1) && (symbol.equals(e.getCoinPair().getName())))
//                .collect(Collectors.toList());
//        List<CoinPairChoice> filterList = coinPairChoiceList.stream()
//                .filter((e) -> symbol.equals(e.getCoinPair().getName()))
//                .collect(Collectors.toList());

        //获取所有用户API
//        List<TradePlatformApi> allApi = tradePlatformApiClientService.findAll();
//        List<TradePlatformApi> allApi = fillTradePlatformApiList();

//        List<String> allApiOnCache = new ArrayList<>(redisTemplate.keys("*_"+symbol));

        //TODO 遍历所有的交易trade
        filterSet.parallelStream().forEach((s)->{
//        keys.parallelStream().forEach((s)->{

            //获取该用户redis中的数据
            String redisKey = s;
            JSONObject trade = tradeMap.get(s);


            System.out.println("每个trade--------->"+trade);

            //String redisKey = "asdf";
//            Object result = redisTemplate.opsForValue().get(redisKey);
//            JSONObject resultJOSNObject = JSON.parseObject(result.toString());

            if (trade == null) {
                return;
            }

            //计算实时收益比   判断买卖
            //持仓费用
            Double positionCost = Double.valueOf(trade.get("position_cost").toString());
            //持仓数量
            Double positionNum = Double.valueOf(trade.get("position_num").toString());
            //实时收益比
            double realTimeEarningRatio = countRealTimeEarningRatio(positionNum,positionCost,price);

            //redis中添加实时收益比
//            updateRedisString(redisKey,"real_time_earning_ratio",realTimeEarningRatio);

            String accessKey = trade.getString("accessKey");
            String secretKey = trade.getString("secretKey");

            if (realTimeEarningRatio >= 1) {

                System.out.println("实时收益比大于1----------判断卖？");
            //if (true) {
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
                int stopProfitType = Integer.valueOf(trade.get("is_use_follow_target_profit").toString());
                double stopProfitPrice = Double.valueOf( targetProfitPrice==null ? "0" : targetProfitPrice.toString());
                double callBackRatio = Double.valueOf(trade.get("turn_down_ratio").toString());
                double stopProfitRatio = Double.valueOf(trade.get("emit_ratio").toString());
                boolean isSell = isSell(positionCost, stopProfitType, stopProfitPrice, stopProfitRatio,
                        realTimeEarningRatio, stopProfitRatio, callBackRatio,redisKey);
                System.out.println("卖？-------------"+isSell);
                if (isSell) {
                    //mq发送卖的消息
                    boolean b = sendMessage(accessKey,secretKey,symbol,"sell");
                }

            } else {
                System.out.println("实时收益比大于1----------判断买？");
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
                System.out.println("买？---------"+isBuy);
                if (isBuy) {
                    //mq发送买的消息
                     boolean b = sendMessage(accessKey,secretKey,symbol,"buy");
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