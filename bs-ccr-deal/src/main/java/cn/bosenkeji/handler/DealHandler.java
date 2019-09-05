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
import java.util.stream.Collectors;


@RestController
public class DealHandler {

    private static final Logger log = LoggerFactory.getLogger(DealHandler.class);

    @Autowired
    private MySource source;

    @Autowired
    private RedisTemplate redisTemplate;

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
//        Set<String> keys = redisTemplate.keys(DealUtil.TRADE_KEYS_PATTERN);

        String setKey = symbol+"_zset";

        //获取redis中对应货币对的zset
        Set<String> keySet = redisTemplate.opsForZSet().rangeByScore(setKey, 1, 1);

        if (CollectionUtils.isEmpty(keySet)) { return; }

        //过滤不是该货币对的key
        Set<String> filterSet = keySet.stream().filter((s) -> s.contains(symbol)).collect(Collectors.toSet());

        //获取key对应的value
//        ConcurrentHashMap<String,JSONObject> tradeMap = new ConcurrentHashMap<>();
        filterSet.parallelStream().forEach((s)->{

            Map trade = redisTemplate.opsForHash().entries(s);

            if (CollectionUtils.isEmpty(trade)) {
                return;
            }

            //初始化
            DealParameter dealParameter = new DealParameterParser(trade).getDealParameter();

            //初始化或获取 java要操作redis的key和value
            RedisParameter redisParameter = DealUtil.javaRedisParameter(dealParameter, redisTemplate);



            //计算实时收益比   判断买卖
            Double positionCost = dealParameter.getPositionCost();
            //持仓数量
            Double positionNum = dealParameter.getPositionNum();
            //实时收益比
            if (positionCost == 0.0) {
                positionCost = 1.0;
            }
            Double realTimeEarningRatio = DealCalculator.countRealTimeEarningRatio(positionNum,positionCost,price);

            //记录实时收益比
            redisTemplate.opsForHash().put(redisParameter.getRedisKey(),DealUtil.REAL_TIME_EARNING_RATIO,realTimeEarningRatio.toString());

            log.info("accessKey:"+ dealParameter.getAccessKey() + "symbol"+ dealParameter.getSymbol()
                    +"  实时收益比："+realTimeEarningRatio);


            //清除 触发追踪止盈标志
            if (redisParameter.getIsTriggerTraceStopProfit() == 1) {

                //触发追踪止盈 但未卖出 实时收益比低于1 取消
                if (
                        (redisParameter.getTriggerStopProfitOrder() < 1 && redisParameter.getTriggerStopProfitOrder() == dealParameter.getFinishedOrder()) ||
                        (redisParameter.getTriggerStopProfitOrder() != dealParameter.getFinishedOrder()) ||
                        (dealParameter.getTradeStatus() == 3)
                ) {
                    DealCalculator.updateRedisHashValue(redisParameter.getRedisKey(),DealUtil.IS_TRIGGER_TRACE_STOP_PROFIT,"0",redisTemplate);
                    return;
                }
            }

            //清除 触发追踪建仓标志
            if (redisParameter.getIsFollowBuild() == 1 || redisParameter.getIsFollowBuild() == 2) {
                //计算实时拟买入均价
                Double v = DealCalculator.countAveragePrice(realTimeTradeParameter.getDeep(), Double.valueOf(dealParameter.getBuyVolume().get(dealParameter.getFinishedOrder().toString()).toString()));

                //获取下调均价 下调均价=(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
                Double lowerAveragePrice = (dealParameter.getPositionCost() - dealParameter.getStoreSplit()) - (dealParameter.getPositionCost()*dealParameter.getFollowLowerRatio());
                if (
                        (v > lowerAveragePrice && redisParameter.getTriggerFollowBuildOrder() == dealParameter.getFinishedOrder()) ||
                        (redisParameter.getTriggerFollowBuildOrder() != dealParameter.getFinishedOrder()) ||
                        (dealParameter.getTradeStatus() == 3)
                ) {
                    DealCalculator.updateRedisHashValue(redisParameter.getRedisKey(),DealUtil.IS_FOLLOW_BUILD,"0",redisTemplate);
                }
            }

            //判断是否交易
            if (dealParameter.getTradeStatus() != 1 && dealParameter.getTradeStatus() != 2) {
                return;
            }


            if (realTimeEarningRatio >= 1) {
//            if (true) {
            //判断是否卖
                boolean isSell = DealCalculator.isSell(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);
                if (isSell) {
                    //mq发送卖的消息
                    boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_SELL,source);
                    log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL
                            +"  消息发送："+isSend + "symbol"+ dealParameter.getSymbol() +"  finished_order:" + dealParameter.getFinishedOrder());
                }

            }

            //判断买
            boolean isBuy = DealCalculator.isBuy(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);

            if (isBuy) {
                //mq发送买的消息
                 boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_BUY,source);
                 log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY
                         +"  消息发送："+isSend + "symbol"+ dealParameter.getSymbol() + "  finished_order:" + dealParameter.getFinishedOrder());
            }
        });

    }

}