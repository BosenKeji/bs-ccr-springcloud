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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RestController
public class DealHandler {

    private static final Logger log = LoggerFactory.getLogger(DealHandler.class);

    private static final String OKEX_PLATFORM_NAME = "okex";

    DealHandler() { }

    @Autowired
    private MySource source;

    @Autowired
    private RedisTemplate redisTemplate;

    @StreamListener("input1")
    private void consumerMessage(String msg) {

        //1、参数处理
        //mq实时报价
        JSONObject jsonObject = JSON.parseObject(msg);  //json格式化
        //mq参数解析
        RealTimeTradeParameter realTimeTradeParameter = new RealTimeTradeParameterParser(jsonObject).getRealTimeTradeParameter();
        //mq参数检测
        boolean b = checkReadTimeParameter(realTimeTradeParameter);
        if (b) {
            log.info("实时价格参数错误！");
        }

        //平台处理
        String setKey = realTimeTradeParameter.getSymbol() + "_zset";
        if (OKEX_PLATFORM_NAME.equals(realTimeTradeParameter.getPlatFormName())) {
            setKey = OKEX_PLATFORM_NAME + "_" + setKey;
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
                return;
            }
            DealParameter dealParameter = new DealParameterParser(trade).getDealParameter();

            //判断是否交易
            if (dealParameter.getTradeStatus() != 1 && dealParameter.getTradeStatus() != 3) {
                return;
            }

            //初始化或获取 java要操作redis的key和value
            RedisParameter redisParameter = DealUtil.javaRedisParameter(dealParameter, redisTemplate);

            //计算实时收益比   判断买卖
            //实时收益比
            Double realTimeEarningRatio = DealCalculator.countRealTimeEarningRatio(realTimeTradeParameter.getBuyDeep(),
                    dealParameter.getPositionNum(),dealParameter.getPositionCost());
            //记录实时收益比
            DealUtil.recordRealTimeEarningRatio(redisParameter.getRedisKey(),realTimeEarningRatio.isNaN() ? "0.0" : realTimeEarningRatio.toString() ,redisTemplate);

            //是否清除 触发追踪止盈标志
            if (redisParameter.getIsTriggerTraceStopProfit() == 1) {
                if (DealUtil.isClearTriggerStopProfit(dealParameter,redisParameter,redisTemplate)) return;
            }

            //是否清除 触发追踪建仓标志
            if (!dealParameter.getFinishedOrder().equals(dealParameter.getMaxTradeOrder())) {
                if (redisParameter.getIsFollowBuild() == 1) {
                    if (DealUtil.isClearTriggerFollowBuild(dealParameter, redisParameter, realTimeTradeParameter, redisTemplate))
                        return;
                }
            }

            if (realTimeEarningRatio >= 1) {
            //判断是否卖
                boolean isSell = DealCalculator.isSell(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);
                if (isSell) {
                    //redis分数置为0
                    DealCalculator.updateRedisSortedSetScore(setKey,s,0.0,redisTemplate);
                    //mq发送卖的消息
                    boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_SELL,source);
                    log.info("sell : " + dealParameter.getSymbol() + "  " + dealParameter.getSignId() + "  " + dealParameter.getFinishedOrder());
                }

            }

            if (dealParameter.getTradeStatus() != 3) {  //交易状态为3 停止买入 正常买出
                //判断买
                boolean isBuy = DealCalculator.isBuy(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);
                if (isBuy) {
                    //redis分数置为0
                    DealCalculator.updateRedisSortedSetScore(setKey,s,0.0,redisTemplate);
                    //mq发送买的消息
                     boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_BUY,source);
                     if (isBuy) {
                         log.info("buy : " + dealParameter.getSymbol() + "  " + dealParameter.getSignId() + "  " + dealParameter.getFinishedOrder());
                     }
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