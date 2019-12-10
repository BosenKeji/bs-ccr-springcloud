package cn.bosenkeji.handler;

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
        String redisKey = jsonObject.getString("key");
        String orderId = DealUtil.getString(jsonObject.get("orderId"));
        String plantFormName = DealUtil.getString(jsonObject.get("plantFormName"));
        OrderGroup orderGroup = transformOrderGroup(jsonObject);
        log.info("redisKey ==>"+redisKey);
        log.info("orderId ==>" + orderId);
        log.info("plantFormName ==>"+plantFormName);
        log.info("orderGroup ==>"+orderGroup.toString());
        int id = orderGroup.getId();

        synchronized (this) {
            if (id <= 0 && orderGroup.getCoinPairChoiceId() > 0){
                Result result = this.iOrderGroupClientService.addOneOrderGroup(orderGroup);
                if (result.getData() == null){
                    log.info("result ==>" + result.getMsg());
                }else{
                    log.info("订单组创建 ==>" + result.getMsg());
//                    OrderGroupIdMQResult orderGroupIdMQResult = new OrderGroupIdMQResult(orderGroup.getCoinPairChoiceId(), Math.abs((int) result.getData()),redisKey,orderId);
//                    if (!plantFormName.isEmpty()){
//                        if (sendGroupId(orderGroupIdMQResult,plantFormName)){
//                            log.info("result ==> " + result.toString());
//                            log.info("orderGroupIdMQResult ==> " + orderGroupIdMQResult.toString());
//                            log.info("订单组id推送成功！");
//                        }
//                    }
                }
            }else {
                Result updateResult = this.iOrderGroupClientService.updateOneOrderGroup(orderGroup);
                log.info("更新订单组成功 ==>"+updateResult.toString());
            }
        }

    }

    @StreamListener("order_input")
    public void consumerTradeOrderMsg(String msg){
        log.info(msg);
        TradeOrder tradeOrder = transformOrder(msg);
        JSONObject jsonObject = JSON.parseObject(msg);
        String groupName = DealUtil.getString(jsonObject.get("name"));
        int groupId = this.iOrderGroupClientService.getIdByName(groupName);
        if (groupId <= 0){
            log.info("订单组id不合法！");
            return;
        }
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
            if(json.get("shell") != null && json.get("extraProfit") != null) {
                double shellProfit = DealUtil.getDouble(json.getDouble("shellProfit"));
                double extraProfit = DealUtil.getDouble(json.getDouble("extraProfit"));
                order.setExtraProfit(extraProfit);
                order.setShellProfit(shellProfit);
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

    private boolean sendGroupId(OrderGroupIdMQResult orderGroupIdMQResult ,String plantFormName){
        Message<OrderGroupIdMQResult> message = MessageBuilder.withPayload(orderGroupIdMQResult).build();
        if ("huobi".equals(plantFormName.toLowerCase())){
            return this.source.groupIdOutPutHB().send(message);
        }
        if ("okex".equals(plantFormName.toLowerCase())){
            return this.source.groupIdOutPutOK().send(message);
        }

        log.info("plantFormName 不合法！");
        return false;
    }


    private OrderGroup transformOrderGroup(JSONObject jsonObject){
        OrderGroup orderGroup  = new OrderGroup();

        String name = DealUtil.getString(jsonObject.get("name"));
        int coinPairChoiceId = Integer.parseInt(jsonObject.getString("coinPairChoiceId"));
        if (jsonObject.get("isEnd") != null){
            int isEnd = DealUtil.getInteger(jsonObject.get("isEnd"));
            if (isEnd == 1){
                double endProfitRatio = DealUtil.getDouble(jsonObject.get("endProfitRatio"));
                int endType = DealUtil.getInteger(jsonObject.getInteger("endType"));
                orderGroup.setEndProfitRatio(endProfitRatio);
                orderGroup.setEndType(endType);
            }
        }

        orderGroup.setName(name);
        orderGroup.setCoinPairChoiceId(coinPairChoiceId);

        return orderGroup;
    }
}
