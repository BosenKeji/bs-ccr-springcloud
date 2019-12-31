package cn.bosenkeji.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author CAJR
 * @date 2019/12/31 3:40 下午
 */
public interface OrderSink {
    @Input("order_group_input")
    SubscribableChannel orderGroupInput();
}
