package cn.bosenkeji.handle;

import cn.bosenkeji.vo.Const;
import cn.bosenkeji.vo.RobotRunStatusParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xivin
 * @email 1250402127@qq.com
 * @description
 * @date 2020/4/23
 */
@Component
public class RobotRunStatusHandle {

    @Resource
    private RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RobotRunStatusHandle.class);

    /**
     * 处理机器人运行状态方法
     * @param msg
     */
    @StreamListener("robot_run_status_input")
    public void get(String msg) {
        System.out.println("  mq consumer msg ======================= " + msg);

        RobotRunStatusParams robotRunStatusParams = new RobotRunStatusParams();
        try {

            JSONObject jsonObject = JSON.parseObject(msg);
            Integer type = (Integer) jsonObject.get("type");
            List<Integer> robotIds = jsonObject.getObject("robotIds", List.class);

            robotRunStatusParams.setType(type);
            robotRunStatusParams.setRobotIds(robotIds);

        }catch (Exception e) {
            e.printStackTrace();
            logger.error("转json失败");
        }

        handle(robotRunStatusParams);
    }

    void handle(RobotRunStatusParams robotRunStatusParams) {
        logger.info(robotRunStatusParams.toString());

        Set<String> robotIds = new HashSet<>();
        robotRunStatusParams.getRobotIds().forEach(robotId -> {
            robotIds.add(String.valueOf(robotId));
        });

        System.out.println("robotIds = " + robotIds);

        if (robotRunStatusParams.getType() == Const.TO_NOT_RUN) {

            long add = redisTemplate.opsForSet().add(Const.NOT_RUN_ROBOT_ID_SET, robotIds.toArray());
            logger.info("=============== 处理 变成不运行状态的机器人个数为 {} ，他们分别是 {} ================",add,robotRunStatusParams.getRobotIds());
        }else if (robotRunStatusParams.getType() == Const.TO_RUN) {
            long remove = redisTemplate.opsForSet().remove(Const.NOT_RUN_ROBOT_ID_SET, robotIds.toArray());
            logger.info("=============== 处理 变成 运行状态的机器人个数为 {} ，他们分别是 {} ================",remove,robotRunStatusParams.getRobotIds());
        }else {
            logger.error("=============状态不对 robotRunStatusParams.type={}  ==============",robotRunStatusParams.getType());
        }

    }

}
