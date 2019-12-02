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

import javax.annotation.Resource;

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
        log.info(redisKey);
        OrderGroup orderGroup = JSONObject.parseObject(msg,OrderGroup.class);
        log.info(orderGroup.toString());
        int id = orderGroup.getId();

        if (id <= 0 && orderGroup.getCoinPairChoiceId() > 0){
            Result result = this.iOrderGroupClientService.addOneOrderGroup(orderGroup);
            if (result.getData() == null){
                log.info(result.getMsg());
            }else{
                OrderGroupIdMQResult orderGroupIdMQResult = new OrderGroupIdMQResult(orderGroup.getCoinPairChoiceId(), (int) result.getData(),redisKey);
                if (sendGroupId(orderGroupIdMQResult)){
                    log.info(orderGroupIdMQResult.toString());
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
        TradeOrder tradeOrder = JSONObject.parseObject(msg,TradeOrder.class);
        Result result = this.iTradeOrderClientService.addOneOrderGroup(tradeOrder);
        log.info("添加订单信息："+result.toString());
    }

    private boolean sendGroupId(OrderGroupIdMQResult orderGroupIdMQResult){
        Message<OrderGroupIdMQResult> message = MessageBuilder.withPayload(orderGroupIdMQResult).build();
        return this.source.groupIdOutPut().send(message);
    }

}
