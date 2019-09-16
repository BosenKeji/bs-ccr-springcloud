package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.combo.UserProductCombo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.mapper
 * @Version V1.0
 * @create 2019-07-23 15:42
 */
@Repository
public class UserProductComboRedisTemplate {

    private final static String PRE_KEY="userProductCombo:id_";

    @Autowired
    private RedisTemplate redisTemplate;

    public void add(UserProductCombo userProductCombo,long time) {
         redisTemplate.opsForValue().set(PRE_KEY+userProductCombo.getId(),userProductCombo.getId()+"",time,TimeUnit.DAYS);
    }

    public UserProductCombo get(int id) {
        UserProductCombo userProductCombo = (UserProductCombo) redisTemplate.opsForValue().get(PRE_KEY+id);
        return userProductCombo;
    }

    public long getExpire(int id) {
        return redisTemplate.getExpire(PRE_KEY+id,TimeUnit.DAYS);
    }

    public void setExpire(int id,long time) {

        redisTemplate.expire(PRE_KEY+id,time,TimeUnit.DAYS);
    }



}
