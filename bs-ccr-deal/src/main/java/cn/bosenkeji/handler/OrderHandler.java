package cn.bosenkeji.handler;

import cn.bosenkeji.lock.DistributedLock;
import cn.bosenkeji.lock.impl.RedisDistributedLock;
import cn.bosenkeji.messaging.MySource;
import cn.bosenkeji.service.IOrderGroupClientService;
import cn.bosenkeji.service.ITradeOrderClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.DealUtil;
import cn.bosenkeji.vo.OrderGroupIdMQResult;
import cn.bosenkeji.vo.transaction.OrderGroup;
import cn.bosenkeji.vo.transaction.TradeOrder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * @author CAJR
 * @date 2019/12/2 4:32 下午
 */
@RestController
public class OrderHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderHandler.class);
    private static final int GROUP_PLUS_ORDER_SIGN = 1;
    private static final int ONLY_ORDER_SIGN = 2;
    private static final String LOCK_KEY = "order-lock";


    @Autowired
    private MySource source;

    @Autowired
    IOrderGroupClientService iOrderGroupClientService;

    @Autowired
    ITradeOrderClientService iTradeOrderClientService;

    @Autowired
    private RedisTemplate redisTemplate;

    @StreamListener("order_group_input")
    public void consumerOrderGroupMsg(String msg){
        log.info(msg);
        JSONObject jsonObject = JSON.parseObject(msg);
        int sign = DealUtil.getInteger(jsonObject.get("sign"));
        String groupName = DealUtil.getString(jsonObject.get("name"));
        log.info("sign ==> "+ sign);

        if (sign == GROUP_PLUS_ORDER_SIGN){
            consumerGroupPlusOrderMsg(msg,jsonObject,groupName);
        }
        else if (sign == ONLY_ORDER_SIGN){
            consumerOnlyOrderMsg(msg,jsonObject,groupName);
        }
        else {
            log.info("队列消息不合法！");
        }
    }

    private void  consumerGroupPlusOrderMsg(String msg,JSONObject jsonObject,String groupName){
        OrderGroup orderGroup = transformOrderGroup(jsonObject);
        log.info("orderGroup ==>"+orderGroup.toString());
        int groupId = orderGroup.getId();
        DistributedLock distributedLock = new RedisDistributedLock(redisTemplate,LOCK_KEY,60);
        String lockLogo = "";
        try {
            //获取锁
            do {
                lockLogo = distributedLock.acquireLock();
            } while (lockLogo == null);

            if (orderGroup.getCoinPairChoiceId() > 0){
                Result result = this.iOrderGroupClientService.addOneOrderGroup(orderGroup);
                if (result.getData() == null){
                    log.info("result ==>" + result.getMsg());
                }else{
                    groupId = Integer.parseInt(result.getData().toString());
                    log.info("订单组创建 ==>" + result.getMsg());
                }
                TradeOrder order = this.transformOrder(msg);
                if (groupId > 0){
                    order.setOrderGroupId(groupId);
                    createOrder(order);
                }else {
                    orderGroup.setId(Math.abs(groupId));
                    Result updateResult = this.iOrderGroupClientService.updateOneOrderGroup(orderGroup);
                    log.info("更新订单组 ==>"+updateResult.toString());
                    order.setOrderGroupId(Math.abs(groupId));
                    createOrder(order);
                }
            }else {
                log.info("自选币id不合法 ==>"+orderGroup.getCoinPairChoiceId());
            }
        }finally {
            log.info("lockLogo ==>"+lockLogo);
            while(true){
                boolean isUnLock = distributedLock.releaseLock(lockLogo);
                if (isUnLock){
                    break;
                }
            }
        }


    }

    private void createOrder(TradeOrder order){
        Result result = this.iTradeOrderClientService.addOneOrderGroup(order);
        if (result.getData() != null){
            if (Integer.parseInt(result.getData().toString()) == 1){
                log.info("首次或尾次订单创建成功！"+result.getMsg());
            }
        } else {
            log.info("首次或尾次订单创建失败！"+result.getMsg());
        }
    }

    private synchronized void  consumerOnlyOrderMsg(String msg,JSONObject jsonObject,String groupName){
        TradeOrder tradeOrder = transformOrder(msg);
        int groupId = this.iOrderGroupClientService.getIdByName(groupName);
        DistributedLock distributedLock = new RedisDistributedLock(redisTemplate,LOCK_KEY,60);
        String lockLogo = "";
        try {
            //获取锁
            do {
                lockLogo = distributedLock.acquireLock();
            } while (lockLogo == null);

            if (groupId > 0){
                tradeOrder.setOrderGroupId(groupId);
                int finishedOrderNumber = -1;
                if (jsonObject.get("finished_order") != null){
                    finishedOrderNumber = Integer.parseInt(jsonObject.getString("finished_order"))+1;
                    log.info("finishedOrderNum ==>" + finishedOrderNumber);
                }

                if (tradeOrder.getOrderGroupId() > 0){
                    int dbOrderNum = (int) this.iTradeOrderClientService.getOrderNumberByGroupId(tradeOrder.getOrderGroupId()).getData();
                    log.info("dbOrderNum ==>" + dbOrderNum);

                    if (dbOrderNum >= 0 && dbOrderNum < finishedOrderNumber){
                        Result result = this.iTradeOrderClientService.addOneOrderGroup(tradeOrder);
                        log.info("添加订单信息："+result.toString());
                    }
                }else {
                    log.info("订单组id不合法或重复添加 添加订单失败！");
                }
            }
        }finally {
            log.info("lockLogo ==>"+lockLogo);
            while(true){
                boolean isUnLock = distributedLock.releaseLock(lockLogo);
                if (isUnLock){
                    break;
                }
            }
        }

    }

    private TradeOrder transformOrder(String msg) {
        TradeOrder order = new TradeOrder();

        JSONObject json = JSON.parseObject(msg);

        int orderGroupId = DealUtil.getInteger(json.get("orderGroupId"));
        double theoreticalBuildPrice = DealUtil.getDouble(json.get("theoreticalBuildPrice"));
        double profitRatio = DealUtil.getDouble(json.get("profitRatio"));
        double tradeAveragePrice = DealUtil.getDouble(json.get("tradeAveragePrice"));
        double tradeNumbers = DealUtil.getDouble(json.get("tradeNumbers"));
        double tradeCost = DealUtil.getDouble(json.get("tradeCost"));
        int tradeType = DealUtil.getInteger(json.get("tradeType"));
        Timestamp createdAt = json.getTimestamp("createdAt");
        if (tradeType > 1){
            if(json.get("sellProfit") != null && json.get("extraProfit") != null) {
                double sellProfit = DealUtil.getDouble(json.getDouble("sellProfit"));
                double extraProfit = DealUtil.getDouble(json.getDouble("extraProfit"));
                order.setExtraProfit(extraProfit);
                order.setSellProfit(sellProfit);
            }
        }

        order.setOrderGroupId(orderGroupId);
        order.setProfitRatio(profitRatio);
        order.setTheoreticalBuildPrice(theoreticalBuildPrice);
        order.setTradeCost(tradeCost);
        order.setTradeAveragePrice(tradeAveragePrice);
        order.setTradeNumbers(tradeNumbers);
        order.setTradeType(tradeType);
        order.setCreatedAt(createdAt);

        return order;
    }

//    private boolean sendGroupId(OrderGroupIdMQResult orderGroupIdMQResult ,String plantFormName){
//        Message<OrderGroupIdMQResult> message = MessageBuilder.withPayload(orderGroupIdMQResult).build();
//        if ("huobi".equals(plantFormName.toLowerCase())){
//            return this.source.groupIdOutPutHB().send(message);
//        }
//        if ("okex".equals(plantFormName.toLowerCase())){
//            return this.source.groupIdOutPutOK().send(message);
//        }
//
//        log.info("plantFormName 不合法！");
//        return false;
//    }


    private OrderGroup transformOrderGroup(JSONObject jsonObject){
        OrderGroup orderGroup  = new OrderGroup();

        String name = DealUtil.getString(jsonObject.get("name"));
        int coinPairChoiceId = Integer.parseInt(jsonObject.getString("coinPairChoiceId"));
        if (jsonObject.get("isEnd") != null){
            int isEnd = DealUtil.getInteger(jsonObject.get("isEnd"));
            orderGroup.setIsEnd(isEnd);
            if (isEnd == 1){
                double endProfitRatio = DealUtil.getDouble(jsonObject.get("endProfitRatio"));
                int endType = DealUtil.getInteger(jsonObject.getInteger("endType"));
                orderGroup.setEndProfitRatio(endProfitRatio);
                orderGroup.setEndType(endType);
            }
        }

        orderGroup.setName(name);
        orderGroup.setCoinPairChoiceId(coinPairChoiceId);

        log.info("填充了数据之后的订单组 ==>"+orderGroup.toString());
        return orderGroup;
    }
}
