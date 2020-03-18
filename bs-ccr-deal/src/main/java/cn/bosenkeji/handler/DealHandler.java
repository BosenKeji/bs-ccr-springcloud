package cn.bosenkeji.handler;

import cn.bosenkeji.enums.DealEnum;
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
import org.springframework.messaging.Message;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * 判断买卖流程
 *
 * @author hjh
 *
 */

@RestController
public class DealHandler {

    private static final Logger log = LoggerFactory.getLogger(DealHandler.class);

    DealHandler() { }

    @Autowired
    private MySource source;

    @Autowired
    private RedisTemplate redisTemplate;

    @StreamListener("huobi_input")
    private void consumerMessage(String msg) {

        //1、参数处理
        //mq实时报价
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(msg);
        } catch (Exception e) {
            log.info("实时价格参数错误" + msg);
            return;
        }
        //mq参数解析
        RealTimeTradeParameter realTimeTradeParameter = new RealTimeTradeParameterParser(jsonObject).getRealTimeTradeParameter();
        //mq参数检测
        boolean b = checkReadTimeParameter(realTimeTradeParameter);
        if (b) {
            log.info("实时价格参数错误！" + realTimeTradeParameter);
        }
        String setKey = realTimeTradeParameter.getSymbol() + "_zset";
        if (DealEnum.OKEX_PLATFORM_NAME.equals(realTimeTradeParameter.getPlatFormName())) {
            setKey = DealEnum.OKEX_PLATFORM_NAME + "_" + setKey;
        }
        realTimeTradeParameter.setSetKey(setKey);
        handle(realTimeTradeParameter);
    }

    private void handle(RealTimeTradeParameter realTimeTradeParameter) {

        String setKey = realTimeTradeParameter.getSetKey();

        //获取redis中对应货币对的zset
        Set<String> keySet = redisTemplate.opsForZSet().rangeByScore(setKey, 1, 1);

        if (CollectionUtils.isEmpty(keySet)) { return; }

        //过滤不是该货币对的key 和旧的key
        Set<String> filterSet = keySet.stream().filter((s) -> {
            String regExg = "^trade-condition_\\S+_\\S+_\\S+";
            Pattern p = Pattern.compile(regExg);
            Matcher m = p.matcher(s);
            return s.contains(realTimeTradeParameter.getSymbol()) && !m.matches();
        }).collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(filterSet)) {
            return;
        }

        filterSet.parallelStream().forEach((s)->{

            Map trade = redisTemplate.opsForHash().entries(s);


            if (CollectionUtils.isEmpty(trade)) {
                log.info(s + "货币的key的交易参数为空！");
                return;
            }
            DealParameter dealParameter = new DealParameterParser(trade).getDealParameter();

            log.info(s + "交易 TradeStatus 为===>" + dealParameter.getTradeStatus());
            //判断是否交易
            if (dealParameter.getTradeStatus() != 1 && !DealEnum.TRADE_STATUS_3.equals(dealParameter.getTradeStatus())) {
                return;
            }

            //初始化或获取 java要操作redis的key和value
            RedisParameter redisParameter = DealUtil.javaRedisParameter(dealParameter,realTimeTradeParameter.getPlatFormName(), redisTemplate);

            //计算实时收益比   判断买卖
            //实时收益比
            Double realTimeEarningRatio = DealCalculator.countRealTimeEarningRatio(realTimeTradeParameter.getBuyDeep(),
                    dealParameter.getPositionNum(),dealParameter.getPositionCost());
            log.info(s+"实时收益比 ==>" + realTimeEarningRatio);
            //记录实时收益比
            DealUtil.recordRealTimeEarningRatio(redisParameter.getRedisKey(),realTimeEarningRatio.isNaN() ? "0.0" : realTimeEarningRatio.toString() ,redisTemplate);

            //是否清除 触发追踪止盈标志
            if (redisParameter.getIsTriggerTraceStopProfit() == 1) {
                if (DealUtil.isClearTriggerStopProfit(dealParameter,redisParameter,redisTemplate)) {
                    log.info(s+"清除 触发追踪止盈标志");
                    return;
                }
            }

            //是否清除 触发追踪建仓标志
            if (!dealParameter.getFinishedOrder().equals(dealParameter.getMaxTradeOrder())) {
                if (redisParameter.getIsFollowBuild() == 1) {
                    if (DealUtil.isClearTriggerFollowBuild(dealParameter, redisParameter, realTimeTradeParameter, redisTemplate)) {
                        log.info(s+"清除 触发追踪建仓标志");
                        return;
                    }
                }
            }

            if (realTimeEarningRatio >= 1) {
            //判断是否卖
                boolean isSell = DealCalculator.isSell(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);
                if (isSell) {
                    //redis分数置为0
                    DealCalculator.updateRedisSortedSetScore(setKey,s,0.0,redisTemplate);
                    //mq发送卖的消息
                    Message<String> messageObject = DealUtil.createMessageObject(dealParameter, realTimeEarningRatio,realTimeTradeParameter.getPlatFormName(), DealEnum.TRADE_TYPE_SELL);
                    boolean isSend = source.huobiOutput().send(messageObject);
                    log.info("sell-" + isSend + "  " + realTimeTradeParameter  + "  " + dealParameter);
                }

            }
            //交易状态为3 停止买入 正常买出
            if (!DealEnum.TRADE_STATUS_3.equals(dealParameter.getTradeStatus())) {
                //判断买
                boolean isBuy = DealCalculator.isBuy(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);
                if (isBuy) {
                    //redis分数置为0
                    DealCalculator.updateRedisSortedSetScore(setKey,s,0.0,redisTemplate);
                    //mq发送买的消息
                    Message<String> messageObject = DealUtil.createMessageObject(dealParameter, realTimeEarningRatio,realTimeTradeParameter.getPlatFormName(), DealEnum.TRADE_TYPE_BUY);
                    boolean isSend = source.huobiOutput().send(messageObject);
                    log.info("buy-" + isSend + "  " + realTimeTradeParameter + "  " + dealParameter);
                }
            }
        });
    }


    private boolean checkReadTimeParameter(RealTimeTradeParameter realTimeTradeParameter) {
        return CollectionUtils.isEmpty(realTimeTradeParameter.getBuyDeep()) ||
                CollectionUtils.isEmpty(realTimeTradeParameter.getSellDeep()) ||
                StringUtils.isEmpty(realTimeTradeParameter.getSymbol()) ||
                StringUtils.isEmpty(realTimeTradeParameter.getPlatFormName());
    }

}