package cn.bosenkeji.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author CAJR
 * @create 2019/7/26 15:46
 */
public interface MySink {

    @Input("input1")
    SubscribableChannel input1();
}
