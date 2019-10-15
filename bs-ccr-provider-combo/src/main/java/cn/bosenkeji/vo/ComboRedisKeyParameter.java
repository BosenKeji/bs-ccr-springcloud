package cn.bosenkeji.vo;

import java.io.Serializable;
import java.util.List;

public class ComboRedisKeyParameter implements Serializable {

    private int redisKeyId;
    private List<Integer> list;

    public int getRedisKeyId() {
        return redisKeyId;
    }

    public void setRedisKeyId(int redisKeyId) {
        this.redisKeyId = redisKeyId;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
