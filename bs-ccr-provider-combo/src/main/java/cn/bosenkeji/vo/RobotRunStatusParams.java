package cn.bosenkeji.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xivin
 * @email 1250402127@qq.com
 * @description
 * @date 2020/4/23
 */
public class RobotRunStatusParams {

    private int type;
    private List<Integer> robotIds;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getRobotIds() {
        return robotIds;
    }

    public void setRobotIds(List<Integer> robotIds) {
        this.robotIds = robotIds;
    }

    @Override
    public String toString() {
        return "RobotRunStatusParams{" +
                "type=" + type +
                ", robotIds=" + robotIds +
                '}';
    }

}
