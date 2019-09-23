package cn.bosenkeji.utils;

import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RealTimeTradeParameter;
import cn.bosenkeji.vo.RedisParameter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    static Double countAveragePosition(Double positionCost, Double positionNum) {
        return positionCost/( positionNum == 0 ? 1.0 : positionNum);
    }


    /**
     * 计算下调均价
     * @param averagePosition 整体持仓均价
     * @param storeSplit 建仓间隔
     * @param followLowerRatio  追踪下调比
     * @return 下调均价 =(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
     */
    static Double countLowerAveragePrice(Double averagePosition, Double storeSplit, Double followLowerRatio) {
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
     * 计算拟买入均价
     * @param sellDeep 卖深度
     * @param quantity 某单交易量
     * @return 拟买入均价
     */
    static Double countAveragePrice(JSONArray sellDeep, Double quantity) {
        Double priceSum = 0.0;
        Double deepSum = 0.0;

        for ( Object obj : sellDeep) {
            JSONArray o = (JSONArray) obj;
            Double d = Double.valueOf(o.get(0).toString());
            Double value = Double.valueOf(o.get(1).toString());
            if (quantity >= value) {
                priceSum = priceSum + d * value;
                quantity = quantity - value;
                deepSum = deepSum + value;
            } else {
                priceSum = priceSum + d * quantity;
                deepSum = deepSum + quantity;
                break;
            }
        }

        return priceSum/deepSum;
    }

    /**
     *
     * 计算实时收益比
     *
     * （深度1×买1价+深度2×买2价+...+深度N补差额数量×买N价）÷持仓费用
     * @param buyDeep 买入的深度报价
     * @param positionNum 持仓数量
     * @param positionCost 持仓费用
     */
    public static Double countRealTimeEarningRatio(JSONArray buyDeep, Double positionNum ,Double positionCost) {
        Double priceSum = 0.0;

        for ( Object obj : buyDeep) {
            JSONArray o = (JSONArray) obj;
            Double price = Double.valueOf(o.get(0).toString());
            Double num = Double.valueOf(o.get(1).toString());
            if (positionNum >= num) {
                priceSum = priceSum + price * num;
                positionNum = positionNum - num;
            } else {
                priceSum = priceSum + price * positionNum;
                break;
            }
        }
        return priceSum/positionCost;

    }

    public static void main(String[] args) {
        JSONArray jsonArray = new JSONArray();
        JSONArray p1 = new JSONArray();
        JSONArray p2 = new JSONArray();
        JSONArray p3 = new JSONArray();

        p1.set(0,"100");
        p1.set(1,"50");

        p2.set(0,"99");
        p2.set(1,"60");

        p3.set(0,"98");
        p3.set(1,"40");

        jsonArray.add(0,p1);
        jsonArray.add(1,p2);
        jsonArray.add(2,p3);

        System.out.println(countRealTimeEarningRatio(jsonArray,123.0,12000.0));

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

        Double historyMaxBenefitRatio = redisParameter.getHistoryMaxBenefitRatio();
        Integer isTriggerTraceStopProfit = redisParameter.getIsTriggerTraceStopProfit();

        String javaRedisKey = redisParameter.getRedisKey();

        //固定比例止盈和追踪止盈  二选一  //是否启动追踪止盈字段
        //固定金额止盈和比例止盈同时存在  //金额止盈或比例止盈达到一个就止盈
        //在参数设置前  不存在金额止盈，只有比例止盈
        //计算实时收益比
        Double realTimeEarningRatio = countRealTimeEarningRatio(realTimeTradeParameter.getBuyDeep(),positionNum,positionCost);

        if (isStopProfitTrace == 1) {

            //追踪止盈逻辑
            //收益比≥1+触发比例？ 追踪止盈
            if (realTimeEarningRatio - (1 + stopProfitRatio) >= 0) {

                //记录 标志进入追踪止盈
                if (isTriggerTraceStopProfit == 0) {
                    updateRedisHashValue(javaRedisKey,DealUtil.IS_TRIGGER_TRACE_STOP_PROFIT,"1",redisTemplate);
                    updateRedisHashValue(javaRedisKey,DealUtil.TRIGGER_STOP_PROFIT_ORDER,dealParameter.getFinishedOrder().toString(),redisTemplate);
                    log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "  symbol:"+ dealParameter.getSymbol()
                            +"触发追踪止盈");
                    return false;
                }

                //记录实时收益比的最高数值
                if (historyMaxBenefitRatio == 0 || historyMaxBenefitRatio - realTimeEarningRatio < 0) {
                    updateRedisHashValue(javaRedisKey,DealUtil.HISTORY_MAX_BENEFIT_RATIO,realTimeEarningRatio.toString(),redisTemplate);
                }
            }
            //
            if (isTriggerTraceStopProfit == 1) {
                //实时收益比≤最高实时收益比-回降比例？ 确定卖出
                if (realTimeEarningRatio - (historyMaxBenefitRatio-callBackRatio) <= 0) {
                    log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "  symbol:"+ dealParameter.getSymbol()
                            +"  卖，追踪止盈模式：实时收益比≤最高实时收益比-回降比例，发送卖出消息" + "  实时收益比:"+realTimeEarningRatio +
                            "  历史最高收益比：" + historyMaxBenefitRatio + "  回调比例：" + callBackRatio);
                    return true;
                }
            }
        } else {
            //固定止盈
            //收益比≥1+止盈比例？ //确定卖出
            if (realTimeEarningRatio - (1 + stopProfitRatio) >= 0) {
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "  symbol:"+ dealParameter.getSymbol()
                        +"  卖，固定止盈模式：收益比≥1+止盈比例，发送卖出消息"+"  实时收益比:"+realTimeEarningRatio + "  止盈比例:"+ stopProfitRatio);
                return true;
            }
        }
        //是否金额止盈 止盈金额为0 不开启金额止盈
        if (stopProfitPrice == 0) {
            return false;
        } else {
            // 金额止盈
            if ((positionCost * (realTimeEarningRatio-1)) - stopProfitPrice >= 0) {
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL + "  symbol:"+ dealParameter.getSymbol()
                        +"  卖，金额止盈，发送卖出消息");
            }
            return (positionCost * (realTimeEarningRatio-1)) - stopProfitPrice >= 0;
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

        JSONArray deep = realTimeTradeParameter.getSellDeep();
        JSONObject buyVolume = dealParameter.getBuyVolume();

        Integer isFollowBuild = redisParameter.getIsFollowBuild();
        Double minAveragePrice = redisParameter.getMinAveragePrice();

        Double averagePosition = countAveragePosition(positionCost,positionNum);

        //是否需要判断？ 达到最大交易单数？
        if ( finishedOrder.equals(maxTradeOrder) ) {
            return false;
        }
        //是否为第一单？ 第一单直接购买
        if ( finishedOrder == 0 ) {
            log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "  symbol:"+ dealParameter.getSymbol()
                    +"  买，首单直接买入，发送买消息");
            return true;
        }
        //设置现价是否小于等于开始策略时现价-建仓间隔*(最大建仓数-1)？
        if ( realTimeTradeParameter.getSellPrice() - (firstOrderPrice-storeSplit*(maxTradeOrder-1)) <= 0 ) {
            return false;
        }

        //计算拟买入均价
        Double quantity = Double.valueOf(buyVolume.get(finishedOrder.toString()).toString());
        Double averagePrice = countAveragePrice(deep,quantity);

        //追踪下调比
        //获取下调均价 下调均价=(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
        Double lowerAveragePrice = countLowerAveragePrice(averagePosition,storeSplit,followLowerRatio);

        //计算回调均价 回调均价=最小均价+整体持仓均价*追踪回调比
        Double callbackAveragePrice = countCallbackAveragePrice(minAveragePrice,averagePosition,followCallbackRatio);

        boolean isBuy = false;

        //拟买入均价小于等于下调均价？ 触发追踪建仓
        if (averagePrice - lowerAveragePrice <= 0) {
            //标志已触发追踪建仓
            if (isFollowBuild == 0) {
                updateRedisHashValue(javaRedisKey,DealUtil.IS_FOLLOW_BUILD,"1",redisTemplate);
                updateRedisHashValue(javaRedisKey,DealUtil.TRIGGER_FOLLOW_BUILD_ORDER,dealParameter.getFinishedOrder().toString(),redisTemplate);
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "  symbol:"+ dealParameter.getSymbol()
                        +"触发追踪建仓");
                return false;
            }

            //记录最小拟买入均价
            if (minAveragePrice == 0 || minAveragePrice > averagePrice) {
                updateRedisHashValue(javaRedisKey,DealUtil.MIN_AVERAGE_PRICE,averagePrice.toString(),redisTemplate);
            }

            //拟买入均价是否大于等于回调均价？ 是则确定买入
            if (averagePrice - callbackAveragePrice >= 0) {
                log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY + "  symbol:"+ dealParameter.getSymbol()
                        +"  买，拟买入均价是否大于等于回调均价， 发送买消息" + "  拟买入均价:"+averagePrice+"  回调均价:"+callbackAveragePrice);
            }

            isBuy = averagePrice - callbackAveragePrice >= 0;
        } else {
            //不在追踪建仓范围，取消追踪建仓标志
            updateRedisHashValue(javaRedisKey,DealUtil.IS_FOLLOW_BUILD,"0",redisTemplate);
        }
        log.info("拟买入均价:" + averagePrice + "  下调均价:" + lowerAveragePrice + "  回调均价:" + callbackAveragePrice + "  最小拟买入均价:" + minAveragePrice);
        return isBuy;
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
    static void updateRedisHashValue(String redisKey, String hashKey, Object value, RedisTemplate redisTemplate) {
        if (redisKey != null && hashKey != null && value != null) redisTemplate.opsForHash().put(redisKey,hashKey,value);
    }

    /**
     * 修改redis ZSet中某个value的score
     * @param redisKey ZSet的key
     * @param setValue ZSet中的value
     * @param score 分数
     * @param redisTemplate 操作redis
     */
    public static void updateRedisSortedSetScore(String redisKey, String setValue, Double score, RedisTemplate redisTemplate) {
        redisTemplate.opsForZSet().add(redisKey,setValue,score);
    }

    /**
     * 处理redis中交易失败置为2的ZSet中的value值
     * @param redisTemplate 操作redis
     */

    public static void handleExceptionTrade(RedisTemplate redisTemplate) {
        Set<String> keys = redisTemplate.keys("*_zset");
        Set<String> exceptionTradeValue;
        Map<String,Set<String>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(keys)) {
            for (String k : keys) {
                exceptionTradeValue = redisTemplate.opsForZSet().rangeByScore(k, 2, 2);
                if (CollectionUtils.isEmpty(exceptionTradeValue)) {
                    continue;
                }
                map.put(k,exceptionTradeValue);
            }
        } else {
            return;
        }
        if (!CollectionUtils.isEmpty(map)) {
            for (String s : map.keySet()) {
                Set<String> values = map.get(s);
                if (!CollectionUtils.isEmpty(values)) {
                    Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
                    for (String v : values) {
                        ZSetOperations.TypedTuple<Object> typedTuple = new DefaultTypedTuple<>(v,1.0);
                        tuples.add(typedTuple);
                    }
                    redisTemplate.opsForZSet().add(s,tuples);
                    log.info("处理交易异常trade，redisKey:" + s + "  value:" + tuples.toString());
                }
            }
        }


    }

}
