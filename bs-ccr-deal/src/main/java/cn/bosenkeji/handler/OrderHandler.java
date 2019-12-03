package cn.bosenkeji.handler;

import cn.bosenkeji.messaging.MySource;
import cn.bosenkeji.service.IOrderGroupClientService;
import cn.bosenkeji.service.ITradeOrderClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.OrderGroupIdMQResult;
import cn.bosenkeji.vo.transaction.OrderGroup;
import cn.bosenkeji.vo.transaction.TradeOrder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
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
    public static final Logger log = LoggerFactory.getLogger(OrderHandler.class);

    @Autowired
    private MySource source;

    @Autowired
    IOrderGroupClientService iOrderGroupClientService;

    @Autowired
    ITradeOrderClientService iTradeOrderClientService;

    @StreamListener("order_group_input")
    public void consumerOrderGroupMsg(String msg){
        log.info(msg);
        JSONObject jsonObject = JSON.parseObject(msg);
        String redisKey = jsonObject.getString("key");
        log.info("redisKey ==>"+redisKey);
        OrderGroup orderGroup = transformOrderGroup(jsonObject);
        log.info("orderGroup ==>"+orderGroup.toString());
        int id = orderGroup.getId();

        if (id <= 0 && orderGroup.getCoinPairChoiceId() > 0){
            Result result = this.iOrderGroupClientService.addOneOrderGroup(orderGroup);
            if (result.getData() == null){
                log.info("result ==>" + result.getMsg());
            }else{
                OrderGroupIdMQResult orderGroupIdMQResult = new OrderGroupIdMQResult(orderGroup.getCoinPairChoiceId(), (int) result.getData(),redisKey);
                if (sendGroupId(orderGroupIdMQResult)){
                    log.info("orderGroupIdMQResult ==> " + orderGroupIdMQResult.toString());
                    log.info("订单组id推送成功！");
                }
            }
        }else {
            Result updateResult = this.iOrderGroupClientService.updateOneOrderGroup(orderGroup);
            log.info(updateResult.toString());
        }
    }

    @StreamListener("order_input")
    public void consumerTradeOrderMsg(String msg){
        log.info(msg);
        TradeOrder tradeOrder = transformOrder(msg);
        if (tradeOrder.getOrderGroupId() > 0){
            Result result = this.iTradeOrderClientService.addOneOrderGroup(tradeOrder);
            log.info("添加订单信息："+result.toString());
        }else {
            log.info("订单组id不合法！添加订单失败！");
        }

    }

    private TradeOrder transformOrder(String msg) {
        TradeOrder order = new TradeOrder();

        JSONObject json = JSON.parseObject(msg);
        int orderGroupId = json.getInteger("orderGroupId");
        double theoreticalBuildPrice = Double.parseDouble(json.getString("theoreticalBuildPrice"));
        double profitRatio = json.getDouble("profitRatio");
        double tradeAveragePrice = json.getDouble("tradeAveragePrice");
        double tradeNumbers = json.getDouble("tradeNumbers");
        double tradeCost = json.getDouble("tradeCost");
        int tradeType = json.getInteger("tradeType");
        Timestamp createdAt = json.getTimestamp("createdAt");
        double shellProfit = json.getDouble("shellProfit");
        double extraProfit = json.getDouble("extraProfit");

        order.setOrderGroupId(orderGroupId);
        order.setProfitRatio(profitRatio);
        order.setTheoreticalBuildPrice(theoreticalBuildPrice);
        order.setTradeCost(tradeCost);
        order.setTradeAveragePrice(tradeAveragePrice);
        order.setTradeNumbers(tradeNumbers);
        order.setTradeType(tradeType);
        order.setCreatedAt(createdAt);
        order.setExtraProfit(extraProfit);
        order.setShellProfit(shellProfit);

        return order;
    }

    private boolean sendGroupId(OrderGroupIdMQResult orderGroupIdMQResult){
        Message<OrderGroupIdMQResult> message = MessageBuilder.withPayload(orderGroupIdMQResult).build();
        return this.source.groupIdOutPut().send(message);
    }


    private OrderGroup transformOrderGroup(JSONObject jsonObject){
        OrderGroup orderGroup  = new OrderGroup();

        String name = jsonObject.getString("name");
        int coinPairChoiceId = jsonObject.getInteger("coinPairChoiceId");
        int isEnd = jsonObject.getInteger("isEnd");
        if (isEnd == 1){
            double endProfitRatio = jsonObject.getDouble("double endProfitRatio");
            int endType = jsonObject.getInteger("endType");
            orderGroup.setEndProfitRatio(endProfitRatio);
            orderGroup.setEndType(endType);
        }
        orderGroup.setName(name);
        orderGroup.setCoinPairChoiceId(coinPairChoiceId);

        return orderGroup;
    }
}
