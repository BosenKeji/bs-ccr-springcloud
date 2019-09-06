package cn.bosenkeji.utils;

import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RealTimeTradeParameter;
import cn.bosenkeji.vo.RedisParameter;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(DealCalculator.class);

    /**
     * 计算持仓均价
     * @param positionCost 持仓费用
     * @param positionNum 持仓数量
     * @return 持仓均价=持仓费用/持仓数量   （持仓数量不能为0）
     */
    private static Double countAveragePosition(Double positionCost, Double positionNum) {
        return positionCost/( positionNum == 0 ? 1.0 : positionNum);
    }


    /**
     * 计算下调均价
     * @param averagePosition 整体持仓均价
     * @param storeSplit 建仓间隔
     * @param followLowerRatio  追踪下调比
     * @return 下调均价 =(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
     */
    public static Double countLowerAveragePrice(Double averagePosition, Double storeSplit, Double followLowerRatio) {
        return (averagePosition - storeSplit) - (averagePosition * followLowerRatio);
    }

    /**
     *
     * 计算回调均价
     * @param minAveragePrice 最小拟买入均价
     * @param averagePosition 持仓均价
     * @param followCallbackRatio 追踪回调比
     * @return 回调均价=最小拟买入均价 + 持仓均价 * 追踪回调比
     */
    private static Double countCallbackAveragePrice(Double minAveragePrice, Double averagePosition ,Double followCallbackRatio) {
        return minAveragePrice + averagePosition * followCallbackRatio;
    }


    /**
     * 计算实时收益比
     * @param number 持仓数量
     * @param cost 持仓费用
     * @param realTimePrice 实时买价
     * @return 实时收益比 = 买价 * 持仓数量 / 持仓费用  精确小数点后4位
     */
    public static Double countRealTimeEarningRatio(Double number, Double cost, Double realTimePrice) {
        return realTimePrice*number/(cost == 0 ? 1.0 : cost);
    }


    /**
     * 计算拟买入均价
     * @param deep 实时深度
     * @param quantity 某单交易量
     * @return 拟买入均价
     */
    public static Double countAveragePrice(Map<Double,Double> deep, Double quantity) {
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
                    updateRedisHashValue(javaRedisKey,DealUtil.IS_TRIGGER_TRACE_STOP_PROFIT,"1",redisTemplate);
                }

                //记录实时收益比的最高数值
                if (historyMaxRiskBenefitRatio == 0 || historyMaxRiskBenefitRatio < realTimeEarningRatio) {
                    updateRedisHashValue(javaRedisKey,DealUtil.HISTORY_MAX_BENEFIT_RATIO,realTimeEarningRatio.toString(),redisTemplate);
                    updateRedisHashValue(javaRedisKey,DealUtil.TRIGGER_STOP_PROFIT_ORDER,dealParameter.getFinishedOrder(),redisTemplate);
                }
                //实时收益比≤最高实时收益比-回降比例？ 确定卖出
                if (realTimeEarningRatio <= (historyMaxRiskBenefitRatio-callBackRatio)) {
                    log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "symbol"+ dealParameter.getSymbol()
                            +"  卖，追踪止盈模式：实时收益比≤最高实时收益比-回降比例，发送卖出消息");
                    return true;
                }
            }
        } else {
            //固定止盈
            //收益比≥1+止盈比例？ //确定卖出
            if (realTimeEarningRatio > (1 + stopProfitRatio)) {
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "symbol"+ dealParameter.getSymbol()
                        +"  卖，固定止盈模式：收益比≥1+止盈比例，发送卖出消息");
                return true;
            }
        }
        //是否金额止盈 止盈金额为0 不开启金额止盈
        if (stopProfitPrice == 0) {
            log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "symbol"+ dealParameter.getSymbol()
                    +"  卖，未达到追踪止盈，金额止盈模式未开启：直接返回");
            return false;
        } else {
            // 金额止盈
            if ((positionCost * (realTimeEarningRatio-1)) >= stopProfitPrice) {
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "symbol"+ dealParameter.getSymbol()
                        +"  卖，金额止盈，发送卖出消息");
            } else {
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "symbol"+ dealParameter.getSymbol()
                        +"  卖，金额止盈未达到，返回");
            }
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

        String javaRedisKey = redisParameter.getRedisKey();
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

        Double averagePosition = countAveragePosition(positionCost,positionNum);

        //是否需要判断？ 达到最大交易单数？
        if ( finishedOrder.equals(maxTradeOrder) ) {
            log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "symbol"+ dealParameter.getSymbol()
                    +"  买，已达到最大交易单数，返回");
            return false;
        }
        //是否为第一单？ 第一单直接购买
        if ( finishedOrder == 0 ) {
            log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "symbol"+ dealParameter.getSymbol()
                    +"  买，首单直接买入，发送买消息");
            return true;
        }
        //设置策略时现价是否小于等于开始策略时现价-建仓间隔*(最大建仓数-1)？
        if ( firstOrderPrice - (firstOrderPrice-storeSplit*(maxTradeOrder-1)) <= 0 ) {
            log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "symbol"+ dealParameter.getSymbol()
                    +"  买，设置策略时现价是否小于等于开始策略时现价-建仓间隔*(最大建仓数-1)， 返回");
            return false;
        }

        //计算拟买入均价
        Double quantity = Double.valueOf(buyVolume.get(finishedOrder.toString()).toString());
        Double averagePrice = countAveragePrice(deep,quantity);

        //追踪下调比
        //获取下调均价 下调均价=(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
        Double lowerAveragePrice = countLowerAveragePrice(averagePosition,storeSplit,followLowerRatio);

        boolean isBuy = false;

        //拟买入均价小于等于下调均价？ 触发追踪建仓
        if (averagePrice <= lowerAveragePrice) {
            //标志已触发追踪建仓
            if (isFollowBuild == 0) {
                updateRedisHashValue(javaRedisKey,DealUtil.IS_FOLLOW_BUILD,"1",redisTemplate);
                updateRedisHashValue(javaRedisKey,DealUtil.TRIGGER_FOLLOW_BUILD_ORDER,dealParameter.getFinishedOrder().toString(),redisTemplate);
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "symbol"+ dealParameter.getSymbol()
                        +"  买，开始追踪建仓， 返回");
                return false;
            }

            //记录最小拟买入均价
            if (minAveragePrice == 0 || minAveragePrice > averagePrice) {
                updateRedisHashValue(javaRedisKey,DealUtil.MIN_AVERAGE_PRICE,averagePrice.toString(),redisTemplate);
            }

            //计算回调均价 回调均价=最小均价+整体持仓均价*追踪回调比
            Double callbackAveragePrice = countCallbackAveragePrice(minAveragePrice,averagePosition,followCallbackRatio);

            //拟买入均价是否大于等于回调均价？ 是则确定买入
            if ((averagePrice >= callbackAveragePrice)) {
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "symbol"+ dealParameter.getSymbol()
                        +"  买，拟买入均价是否大于等于回调均价， 发送买消息");
            } else {
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "symbol"+ dealParameter.getSymbol()
                        +"  买，拟买入均价是否小于回调均价， 返回");
            }

            isBuy = (averagePrice >= callbackAveragePrice);
        } else {
            //不在追踪建仓范围，取消追踪建仓标志
            updateRedisHashValue(javaRedisKey,DealUtil.IS_FOLLOW_BUILD,"0",redisTemplate);
        }

        return isBuy;
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
     * @param hashKey hash的key
     * @param value hash中key对应的值
     *
     **/
    public static void updateRedisHashValue(String redisKey, String hashKey, Object value, RedisTemplate redisTemplate) {
        if (redisKey != null && hashKey != null && value != null) redisTemplate.opsForHash().put(redisKey,hashKey,value);
    }

}
