package cn.bosenkeji.utils;

import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RealTimeTradeParameter;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Map;

/**
 *
 * deal公式计算 和 买卖逻辑的判断
 *
 * @author hjh
 *
 */

public class DealCalculator {


    /**
     * 计算实时收益比  买价*持仓数量/持仓费用  精确小数点后4位
     * @param number 持仓数量
     * @param cost 持仓费用
     * @param realTimePrice 实时买价
     * @return 实时收益比
     */
    public static double countRealTimeEarningRatio(double number, double cost, double realTimePrice) {
        return realTimePrice*number/cost;
    }


    /**
     * 计算拟买入均价
     * @param deep 实时深度
     * @param quantity 某单交易量
     * @return 拟买入均价
     */
    private static double countAveragePrice(Map<Double,Double> deep, double quantity) {
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

    public static boolean isSell(DealParameter dealParameter, RealTimeTradeParameter realTimeTradeParameter, RedisTemplate redisTemplate) {
        String redisKey = dealParameter.getRedisKey();

        Integer stopProfitType = dealParameter.getIsUseFollowTargetProfit();
        Double stopProfitPrice = dealParameter.getTargetProfitPrice();
        Double callBackRatio = dealParameter.getTurnDownRatio();

        Double stopProfitRatio = dealParameter.getEmitRatio();
        Double positionCost = dealParameter.getPositionCost();
        Double positionNum = dealParameter.getPositionNum();

        Double price = realTimeTradeParameter.getPrice();
        JSONObject jsonObject = dealParameter.getJsonObject();
        Double historyMaxRiskBenefitRatio = dealParameter.getHistoryMaxRiskBenefitRatio();

        Integer isTriggerTraceStopProfit = dealParameter.getIsTriggerTraceStopProfit();

        //读取止盈金额和止盈比例，两种止盈方式，达到一种即可 参数传入
        //判断是否启用追踪止盈 if-else  1为追踪止盈，2为固定止盈
        boolean isStopProfitTrace = stopProfitType==1 ;
        //判断是否开启金额止盈
        boolean isStopProfitPrice = stopProfitPrice!=1 ;

        //计算实时收益比
        Double realTimeEarningRatio = countRealTimeEarningRatio(positionNum,positionCost,price);

        if (isStopProfitTrace) {
            //追踪止盈逻辑
            //收益比≥1+触发比例？ 追踪止盈
            if (realTimeEarningRatio >= (1 + stopProfitPrice)) {
                //if (true) {


                //记录 标志进入追踪止盈
                if (isTriggerTraceStopProfit == 0) {
                    String s = updateJson(jsonObject, DealParameterParser.IS_TRIGGER_TRACE_STOP_PROFIT, 1);
                    updateRedisString(redisKey,s,redisTemplate);
                }

                //记录实时收益比的最高数值
                if (historyMaxRiskBenefitRatio == 0 || historyMaxRiskBenefitRatio < realTimeEarningRatio) {
                    String s = updateJson(jsonObject, DealParameterParser.HISTORY_MAX_RISK_BENEFIT_RATIO, realTimeEarningRatio);
                    updateRedisString(redisKey,s,redisTemplate);
                }
                //实时收益比≤最高实时收益比-回降比例？ 确定卖出
                if (realTimeEarningRatio <= (historyMaxRiskBenefitRatio-callBackRatio)) {
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
        return (isStopProfitPrice && (positionCost * (realTimeEarningRatio-1)) >= stopProfitPrice);

    }

    /**
     *
     * 是否买的逻辑
     * @return 是否买
     */

//    public static boolean isBuy(int orderNumber, int maxOrderNumber,
//                          double averagePosition, double buildPositionInterval,double averagePrice,
//                          double followLowerRatio,double followCallbackRatio,double minAveragePrice,
//                          double firstOrderPrice,String redisKey
//    ) {
    public static boolean isBuy(DealParameter dealParameter, RealTimeTradeParameter realTimeTradeParameter, RedisTemplate redisTemplate) {

        String redisKey = dealParameter.getRedisKey();

        Integer finishedOrder = dealParameter.getFinishedOrder();
        Integer maxTradeOrder = dealParameter.getMaxTradeOrder();
        Double firstOrderPrice = dealParameter.getFirstOrderPrice();

        Double storeSplit = dealParameter.getStoreSplit();
        Double followLowerRatio = dealParameter.getFollowLowerRatio();
        Double followCallbackRatio = dealParameter.getFollowCallbackRatio();

        Double minAveragePrice = dealParameter.getMinAveragePrice();
        Double positionCost = dealParameter.getPositionCost();
        Double positionNum = dealParameter.getPositionNum();

        Map<Double, Double> deep = realTimeTradeParameter.getDeep();
        JSONObject buyVolume = dealParameter.getBuyVolume();
        JSONObject jsonObject = dealParameter.getJsonObject();
        Integer isFollowBuild = dealParameter.getIsFollowBuild();

        if (positionNum == 0) {
            positionNum = 1.0;
        }
        Double averagePosition = positionCost/positionNum;

        //是否需要判断？ 达到最大交易单数？
        if ( finishedOrder.equals(maxTradeOrder) ) {
            return false;
        }
        //是否为第一单？ 第一单直接购买
        if ( finishedOrder == 0 ) {
            return true;
        }
        //设置策略时现价是否小于等于整体持仓均价-建仓间隔*最大建仓数减1？
        if ( firstOrderPrice > (storeSplit*(maxTradeOrder-1)) ) {
            return false;
        }

        //计算拟买入均价
        Double quantity = Double.valueOf(buyVolume.get(finishedOrder.toString()).toString());
        Double averagePrice = countAveragePrice(deep,quantity);

        //追踪下调比
        //获取下调均价 下调均价=(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
        double lowerAveragePrice = (averagePosition - storeSplit) - (averagePosition*followLowerRatio);

        //拟买入均价小于等于下调均价？ 触发追踪建仓
        if (averagePrice > lowerAveragePrice) {
            //标志已触发追踪建仓
            if (isFollowBuild == 0) {
                String s = updateJson(jsonObject, DealParameterParser.IS_FOLLOW_BUILD, 1);
                updateRedisString(redisKey,s,redisTemplate);
            }
            return false;
        }

        //计算回调均价 回调均价=最小均价+整体持仓均价*追踪回调比
        double callbackAveragePrice = minAveragePrice + averagePosition*followCallbackRatio;

        //记录最小拟买入均价
        if (minAveragePrice == 0 || minAveragePrice > averagePrice) {
            String s = updateJson(jsonObject,DealParameterParser.MIN_AVERAGE_PRICE,averagePrice);
            updateRedisString(redisKey,s,redisTemplate);
        }


        //拟买入均价是否大于等于回调均价？ 是则确定买入
        return (averagePrice >= callbackAveragePrice);
    }


    private static String updateJson(JSONObject jsonObject, String key, Object o) {

        JSONObject replace = null;

        if (o instanceof String) {
            replace = (JSONObject) jsonObject.put(key,o.toString());
        }

        if (o instanceof Integer) {
            replace = (JSONObject) jsonObject.put(key,Integer.valueOf(o.toString()));
        }

        if (o instanceof Double) {
            replace = (JSONObject) jsonObject.put(key,Double.valueOf(o.toString()));
        }

        if (replace != null) return replace.toJSONString();
        return null;
    }


    private static void updateRedisString(String redisKey, String value, RedisTemplate redisTemplate) {
        if (redisKey != null && value != null) redisTemplate.opsForValue().set(redisKey, value);
    }

}