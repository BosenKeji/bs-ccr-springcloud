package cn.bosenkeji.utils;

import cn.bosenkeji.vo.RobotRunStatusParams;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author xivin
 * @email 1250402127@qq.com
 * @description
 * @date 2020/4/28
 */
public class RocketMqUtil {

    public static Message<String> generateRobotRunStatusMessage(RobotRunStatusParams robotRunStatusParams) {

        String tags = "=== type : "+robotRunStatusParams.getType() + "===== and robotIds : "+robotRunStatusParams.getRobotIds()+"  ///";
        return MessageBuilder.withPayload(JSON.toJSONString(robotRunStatusParams)).setHeader(RocketMQHeaders.TAGS,tags).build();

    }

}
