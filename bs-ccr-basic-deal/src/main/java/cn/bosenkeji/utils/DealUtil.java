package cn.bosenkeji.utils;

import cn.bosenkeji.enums.DealEnum;
import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RealTimeTradeParameter;
import cn.bosenkeji.vo.RedisParameter;
import cn.bosenkeji.vo.RocketMQResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * deal模块通用的方法
 *
 * @author hjh
 */

public class DealUtil {


    /**
     *
     * 清除触发追踪建仓标志
     *
     * @param dealParameter  买卖需要的参数
     * @param redisParameter  java管理redis的数据
     * @param realTimeTradeParameter  实时交易信息
     * @param redisTemplate  操作redis
     *
     */
    public static Boolean isClearTriggerFollowBuild(DealParameter dealParameter, RedisParameter redisParameter, RealTimeTradeParameter realTimeTradeParameter, RedisTemplate redisTemplate) {
        //计算实时拟买入均价
        Double averagePrice = DealCalculator.countAveragePrice(realTimeTradeParameter.getSellDeep(), Double.valueOf(dealParameter.getBuyVolume().get(dealParameter.getFinishedOrder().toString()).toString()));

        //获取下调均价 下调均价=(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
        Double averagePosition = DealCalculator.countAveragePosition(dealParameter.getPositionCost(),dealParameter.getPositionNum());
        Double lowerAveragePrice = DealCalculator.countLowerAveragePrice(averagePosition,dealParameter.getStoreSplit(),dealParameter.getFollowLowerRatio());


        boolean isClearTriggerFollowBuild = (averagePrice - lowerAveragePrice) > 0 && redisParameter.getTriggerFollowBuildOrder().equals(dealParameter.getFinishedOrder()) ||
                !(redisParameter.getTriggerFollowBuildOrder().equals(dealParameter.getFinishedOrder())) ||
                (dealParameter.getTradeStatus() == 3);

        if (isClearTriggerFollowBuild) {
            DealCalculator.updateRedisHashValue(redisParameter.getRedisKey(), DealEnum.IS_FOLLOW_BUILD,"0",redisTemplate);
            DealCalculator.updateRedisHashValue(redisParameter.getRedisKey(),DealEnum.TRIGGER_FOLLOW_BUILD_ORDER,"0",redisTemplate);
            DealCalculator.updateRedisHashValue(redisParameter.getRedisKey(),DealEnum.MIN_AVERAGE_PRICE,"1000000.0",redisTemplate);
            return true;
        }
        return false;
    }

    /**
     * 清除触发追踪止盈标志
     *
     * @param dealParameter  判断买卖需要的参数
     * @param redisParameter  java管理redis的数据
     * @param redisTemplate  操作redis
     */
    public static Boolean isClearTriggerStopProfit(DealParameter dealParameter, RedisParameter redisParameter, RedisTemplate redisTemplate) {
        boolean isRealTimeEarningRatio = redisParameter.getRealTimeEarningRatio() < 1;
        boolean isUniformOrder = redisParameter.getTriggerStopProfitOrder().equals(dealParameter.getFinishedOrder());
        boolean isTradeStatus = dealParameter.getTradeStatus() == 3;

        boolean isCleanTriggerStopProfit = (isRealTimeEarningRatio && isUniformOrder) || !isUniformOrder || isTradeStatus;

        if (isCleanTriggerStopProfit) {
            DealCalculator.updateRedisHashValue(redisParameter.getRedisKey(),DealEnum.IS_TRIGGER_TRACE_STOP_PROFIT,"0",redisTemplate);
            DealCalculator.updateRedisHashValue(redisParameter.getRedisKey(),DealEnum.TRIGGER_STOP_PROFIT_ORDER,"0",redisTemplate);
            DealCalculator.updateRedisHashValue(redisParameter.getRedisKey(),DealEnum.HISTORY_MAX_BENEFIT_RATIO,"0",redisTemplate);
            return true;
        }
        return false;
    }

    /**
     * 记录实时收益比  REAL_TIME_EARNING_RATIO = "real_time_earning_ratio"
     *
     * @param redisKey redis中hash的key
     * @param hashValue redis中hashKey=REAL_TIME_EARNING_RATIO 对应的hashValue
     * @param redisTemplate redisTemplate
     */
    public static void recordRealTimeEarningRatio(String redisKey, String hashValue, RedisTemplate redisTemplate) {
        redisTemplate.opsForHash().put(redisKey,DealEnum.REAL_TIME_EARNING_RATIO,hashValue);
    }


    /**
     *
     * MQ发送消息
     *
     * @param dealParameter 消息体的数据
     * @param type 买或卖的类型
     * @return 需要发送消息的对象
     */

    public static Message<String> createMessageObject(DealParameter dealParameter, String platformName, String type) {
        RocketMQResult rocketMQResult = new RocketMQResult();

        String symbol = dealParameter.getSymbol();
        String signId = dealParameter.getSignId();
        rocketMQResult.setSymbol(symbol);
        rocketMQResult.setSignId(signId);
        rocketMQResult.setType(type);
        rocketMQResult.setFinished_order(dealParameter.getFinishedOrder());
        rocketMQResult.setPlantFormName(platformName);

        JSONObject jsonResult = (JSONObject) JSONObject.toJSON(rocketMQResult);
        Message<String> jsonMessage = MessageBuilder.withPayload(jsonResult.toJSONString()).build();

        return jsonMessage;
    }


    /**
     *
     * java 操作redis的数据  初始化或获取
     *
     * @param dealParameter 需要交易的参数
     * @param redisTemplate 操作redis
     * @return parameter java 获取redis的结果集
     */

    public static RedisParameter javaRedisParameter(DealParameter dealParameter, String platFormName, RedisTemplate redisTemplate) {

        RedisParameter parameter = new RedisParameter();

        String javaRedisKey = "trade-java_" + dealParameter.getSignId() + "_" + dealParameter.getSymbol();
        if (DealEnum.OKEX_PLATFORM_NAME.equals(platFormName)) {
            javaRedisKey = DealEnum.OKEX_PLATFORM_NAME + "-" + javaRedisKey;
        }

        Map entries = redisTemplate.opsForHash().entries(javaRedisKey);
        if (CollectionUtils.isEmpty(entries)) {
            //初始化数据
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(DealEnum.IS_FOLLOW_BUILD,"0");
            map.put(DealEnum.IS_TRIGGER_TRACE_STOP_PROFIT,"0");
            map.put(DealEnum.MIN_AVERAGE_PRICE,"1000000.0");
            map.put(DealEnum.HISTORY_MAX_BENEFIT_RATIO,"0.0");
            map.put(DealEnum.REAL_TIME_EARNING_RATIO,"0.0");
            map.put(DealEnum.TRIGGER_FOLLOW_BUILD_ORDER,"0");
            map.put(DealEnum.TRIGGER_STOP_PROFIT_ORDER,"0");

            redisTemplate.opsForHash().putAll(javaRedisKey,map);

            parameter.setRedisKey(javaRedisKey);
            parameter.setIsTriggerTraceStopProfit(0);
            parameter.setIsFollowBuild(0);
            parameter.setMinAveragePrice(1000000.0);
            parameter.setHistoryMaxBenefitRatio(0.0);
            parameter.setRealTimeEarningRatio(0.0);
            parameter.setTriggerFollowBuildOrder(0);
            parameter.setTriggerStopProfitOrder(0);

        } else {
            //获取数据
            parameter.setRedisKey(javaRedisKey);
            parameter.setIsTriggerTraceStopProfit(DealUtil.getInteger(entries.get(DealEnum.IS_TRIGGER_TRACE_STOP_PROFIT)));
            parameter.setIsFollowBuild(DealUtil.getInteger(entries.get(DealEnum.IS_FOLLOW_BUILD)));
            parameter.setMinAveragePrice(DealUtil.getDouble(entries.get(DealEnum.MIN_AVERAGE_PRICE)));
            parameter.setHistoryMaxBenefitRatio(DealUtil.getDouble(entries.get(DealEnum.HISTORY_MAX_BENEFIT_RATIO)));
            parameter.setRealTimeEarningRatio(DealUtil.getDouble(entries.get(DealEnum.REAL_TIME_EARNING_RATIO)));
            parameter.setTriggerFollowBuildOrder(DealUtil.getInteger(entries.get(DealEnum.TRIGGER_FOLLOW_BUILD_ORDER)));
            parameter.setTriggerStopProfitOrder(DealUtil.getInteger(entries.get(DealEnum.TRIGGER_STOP_PROFIT_ORDER)));
        }
        return parameter;
    }




    static String getString(Object o) {
        return o == null ? "" : o.toString();
    }

    static Integer getInteger(Object o) {
        Integer temp = 0;
        if (o != null) {
            temp = Integer.valueOf(o.toString());
        }
        return temp;
    }

    static Double getDouble(Object o) {
        Double temp;
        temp = o == null ? Double.valueOf("0.0") : Double.valueOf(o.toString());
        return temp;
    }
}
