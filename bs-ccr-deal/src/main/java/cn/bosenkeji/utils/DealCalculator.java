package cn.bosenkeji.utils;

import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RealTimeTradeParameter;
import cn.bosenkeji.vo.RedisParameter;
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
                break;
            }
        }
        return priceSum/deepSum;
    }

    /**
     *
     * 判断是否卖出
     *
     * @param dealParameter 买卖需要的参数
     * @param realTimeTradeParameter 实时价格、深度...
     * @param redisTemplate 操作redis
     * @return 是否卖
     */
    public static boolean isSell(DealParameter dealParameter, RealTimeTradeParameter realTimeTradeParameter,
                                 RedisParameter redisParameter, RedisTemplate redisTemplate) {

        Integer isStopProfitTrace = dealParameter.getIsStopProfitTrace(); //是否启用追踪止盈
        Double stopProfitPrice = dealParameter.getTargetProfitPrice();
        Double callBackRatio = dealParameter.getTurnDownRatio();

        Double stopProfitRatio = dealParameter.getEmitRatio();
        Double positionCost = dealParameter.getPositionCost();
        Double positionNum = dealParameter.getPositionNum();

        Double price = realTimeTradeParameter.getPrice();

        Double historyMaxRiskBenefitRatio = redisParameter.getHistoryMaxBenefitRatio();
        Integer isTriggerTraceStopProfit = redisParameter.getIsTriggerTraceStopProfit();

        JSONObject jsonObject = redisParameter.getJsonObject();  //java操作redis的JSON对象
        String javaRedisKey = redisParameter.getRedisKey();

        //固定比例止盈和追踪止盈  二选一  //是否启动追踪止盈字段
        //固定金额止盈和比例止盈同时存在  //金额止盈或比例止盈达到一个就止盈

        //在参数设置前  不存在金额止盈，只有比例止盈

        //计算实时收益比
        Double realTimeEarningRatio = countRealTimeEarningRatio(positionNum,positionCost,price);

        if (isStopProfitTrace == 1) {
            //追踪止盈逻辑
            //收益比≥1+触发比例？ 追踪止盈
            if (realTimeEarningRatio >= (1 + stopProfitRatio)) {
                //if (true) {

                //记录 标志进入追踪止盈
                if (isTriggerTraceStopProfit == 0) {
                    JSONObject s = updateJson(jsonObject, DealUtil.IS_TRIGGER_TRACE_STOP_PROFIT, 1);
                    updateRedisString(javaRedisKey,s,redisTemplate);
                }

                //记录实时收益比的最高数值
                if (historyMaxRiskBenefitRatio == 0 || historyMaxRiskBenefitRatio < realTimeEarningRatio) {
                    JSONObject s = updateJson(jsonObject, DealUtil.HISTORY_MAX_BENEFIT_RATIO, realTimeEarningRatio);
                    updateRedisString(javaRedisKey,s,redisTemplate);
                }
                //实时收益比≤最高实时收益比-回降比例？ 确定卖出
                if (realTimeEarningRatio <= (historyMaxRiskBenefitRatio-callBackRatio)) {
                    return true;
                }
            }
        } else {
            //固定止盈
            //收益比≥1+止盈比例？ //确定卖出
            if (realTimeEarningRatio > (1 + stopProfitRatio)) {
                return true;
            }
        }
        //是否金额止盈 止盈金额为0 不开启金额止盈
        if (stopProfitPrice == 0) {
            return false;
        } else {
            // 金额止盈
            return (positionCost * (realTimeEarningRatio-1)) >= stopProfitPrice;
        }

    }

    /**
     *
     * 是否买入
     *
     * @param dealParameter 买卖需要的参数
     * @param realTimeTradeParameter 实时价格、深度...
     * @param redisTemplate 操作redis
     * @return 是否买
     */

    public static boolean isBuy(DealParameter dealParameter, RealTimeTradeParameter realTimeTradeParameter,
                                RedisParameter redisParameter,RedisTemplate redisTemplate) {

        Integer finishedOrder = dealParameter.getFinishedOrder();
        Integer maxTradeOrder = dealParameter.getMaxTradeOrder();

        Double firstOrderPrice = dealParameter.getFirstOrderPrice();
        Double storeSplit = dealParameter.getStoreSplit();
        Double followLowerRatio = dealParameter.getFollowLowerRatio();

        Double followCallbackRatio = dealParameter.getFollowCallbackRatio();
        Double positionCost = dealParameter.getPositionCost();
        Double positionNum = dealParameter.getPositionNum();

        Map<Double, Double> deep = realTimeTradeParameter.getDeep();
        JSONObject buyVolume = dealParameter.getBuyVolume();

        Integer isFollowBuild = redisParameter.getIsFollowBuild();
        Double minAveragePrice = redisParameter.getMinAveragePrice();

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
        //设置策略时现价是否小于等于开始策略时现价-建仓间隔*(最大建仓数-1)？
        if ( firstOrderPrice - (firstOrderPrice-storeSplit*(maxTradeOrder-1)) <= 0 ) {
            return false;
        }

        //计算拟买入均价
        Double quantity = Double.valueOf(buyVolume.get(finishedOrder.toString()).toString());
        Double averagePrice = countAveragePrice(deep,quantity);

        //追踪下调比
        //获取下调均价 下调均价=(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
        double lowerAveragePrice = (averagePosition - storeSplit) - (averagePosition*followLowerRatio);

        //拟买入均价小于等于下调均价？ 触发追踪建仓
        if (averagePrice <= lowerAveragePrice) {
            //标志已触发追踪建仓
            if (isFollowBuild == 0) {
                JSONObject s = updateJson(redisParameter.getJsonObject(), DealUtil.IS_FOLLOW_BUILD, 1);
                updateRedisString(redisParameter.getRedisKey(),s,redisTemplate);
                return false;
            }
        }

        //计算回调均价 回调均价=最小均价+整体持仓均价*追踪回调比
        double callbackAveragePrice = minAveragePrice + averagePosition*followCallbackRatio;

        //记录最小拟买入均价
        if (minAveragePrice == 0 || minAveragePrice > averagePrice) {
            JSONObject s = updateJson(redisParameter.getJsonObject(),DealUtil.MIN_AVERAGE_PRICE,averagePrice);
            updateRedisString(redisParameter.getRedisKey(),s,redisTemplate);
        }

        //拟买入均价是否大于等于回调均价？ 是则确定买入
        return (averagePrice >= callbackAveragePrice);
    }


    /**
     *
     *  更新Json对象的值
     * @param jsonObject 需要更新的jsonObject
     * @param key json中的key
     * @param o 值
     * @return JSONObject
     **/
    private static JSONObject updateJson(JSONObject jsonObject, String key, Object o) {

        if (o instanceof Integer) {
            jsonObject.put(key,Integer.valueOf(o.toString()));
        } else {
            jsonObject.put(key,o);
        }
        return jsonObject;
    }

    /**
     *
     * 更新redis的值
     *
     * @param redisKey 需要修改redis的key
     * @param redisTemplate  redisTemplate
     * @param value 值
     *
     **/
    private static void updateRedisString(String redisKey, JSONObject value, RedisTemplate redisTemplate) {
        if (redisKey != null && value != null) redisTemplate.opsForValue().set(redisKey, value.toJSONString());
    }

}
