package cn.bosenkeji.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLockUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean lock(String lockKey, String lockValue, int expireTime) {

        return redisTemplate.opsForValue().setIfAbsent(lockKey,lockValue,expireTime,TimeUnit.SECONDS);

    }

    public boolean unLock(String lockKey, String lockValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script,Long.class);
        redisScript.setScriptText(script);
        List<String> keys = new ArrayList<>();
        keys.add(lockKey);

        Object execute = redisTemplate.execute(redisScript, keys, lockValue);
        if (execute == null) {
            return false;
        }
        return Integer.valueOf(execute.toString()).equals(1);
    }

}


