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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@RestController
public class DealHandler {

    private static final int threadNum = Runtime.getRuntime().availableProcessors();
    private ThreadPoolExecutor threadPoolExecutor;
    private static final Logger log = LoggerFactory.getLogger(DealHandler.class);

    DealHandler() {
        threadPoolExecutor = new ThreadPoolExecutor(threadNum,threadNum*2,
                3, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    @Autowired
    private MySource source;

    @Autowired
    private RedisTemplate redisTemplate;

    @StreamListener("input1")
    private void consumerMessage(String msg) {
        threadPoolExecutor.execute(() -> {
            handle(msg);
        });
    }

    private void handle(String msg) {

        //将json字符串转换为json对象
        JSONObject jsonObject = JSON.parseObject(msg);

        //获取实时价格
        RealTimeTradeParameter realTimeTradeParameter = new RealTimeTradeParameterParser(jsonObject).getRealTimeTradeParameter();
        JSONArray buyDeep = realTimeTradeParameter.getBuyDeep();
        JSONArray sellDeep = realTimeTradeParameter.getSellDeep();
        String symbol = realTimeTradeParameter.getSymbol();

        //mq参数不正确
        if (CollectionUtils.isEmpty(buyDeep) || CollectionUtils.isEmpty(sellDeep) || symbol == null) {
            return;
        }

        String setKey = symbol+"_zset";

        //获取redis中对应货币对的zset
        Set<String> keySet = redisTemplate.opsForZSet().rangeByScore(setKey, 1, 1);

        //处理异常买卖的trade
        DealCalculator.handleExceptionTrade(redisTemplate);

        if (CollectionUtils.isEmpty(keySet)) { return; }

        //过滤不是该货币对的key
        Set<String> filterSet = keySet.stream().filter((s) -> s.contains(symbol)).collect(Collectors.toSet());

        filterSet.parallelStream().forEach((s)->{

            Map trade = redisTemplate.opsForHash().entries(s);

            if (CollectionUtils.isEmpty(trade)) {
                return;
            }

            //初始化
            DealParameter dealParameter = new DealParameterParser(trade).getDealParameter();

            //初始化或获取 java要操作redis的key和value
            RedisParameter redisParameter = DealUtil.javaRedisParameter(dealParameter, redisTemplate);

            //判断是否交易
            if (dealParameter.getTradeStatus() != 1 && dealParameter.getTradeStatus() != 2) {
                return;
            }

            //计算实时收益比   判断买卖
            //实时收益比
            Double realTimeEarningRatio = DealCalculator.countRealTimeEarningRatio(buyDeep,
                    dealParameter.getPositionNum(),dealParameter.getPositionCost());
            //记录实时收益比
            DealUtil.recordRealTimeEarningRatio(redisParameter.getRedisKey(),realTimeEarningRatio.toString(),redisTemplate);

            log.info("accessKey:"+ dealParameter.getAccessKey() + "  symbol:"+ dealParameter.getSymbol()
                    +"  实时收益比:"+realTimeEarningRatio + "  num:"+ dealParameter.getPositionNum() + "  cost:" + dealParameter.getPositionCost());

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
                    log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_SELL
                            +"  消息发送:"+isSend + "  symbol:"+ dealParameter.getSymbol() +"  finished_order:" + dealParameter.getFinishedOrder());
                }

            }

            //判断买
            boolean isBuy = DealCalculator.isBuy(dealParameter,realTimeTradeParameter,redisParameter,redisTemplate);

            if (isBuy) {
                //redis分数置为0
                DealCalculator.updateRedisSortedSetScore(setKey,s,0.0,redisTemplate);
                //mq发送买的消息
                 boolean isSend = DealUtil.sendMessage(dealParameter,DealUtil.TRADE_TYPE_BUY,source);
                 log.info("accessKey:"+ dealParameter.getAccessKey()+"  type:"+DealUtil.TRADE_TYPE_BUY
                         +"  消息发送:"+isSend + "  symbol:"+ dealParameter.getSymbol() + "  finished_order:" + dealParameter.getFinishedOrder());
            }
        });
    }

}