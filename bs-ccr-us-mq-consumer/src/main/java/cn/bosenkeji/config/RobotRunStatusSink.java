package cn.bosenkeji.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author xivin
 * @email 1250402127@qq.com
 * @description
 * @date 2020/4/23
 */
public interface RobotRunStatusSink {

    @Input("robot_run_status_input")
    SubscribableChannel myInput();

}
