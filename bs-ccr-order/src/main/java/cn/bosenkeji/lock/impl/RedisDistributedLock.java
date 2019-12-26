package cn.bosenkeji.lock.impl;

import cn.bosenkeji.lock.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author CAJR
 * @date 2019/12/10 2:37 下午
 */
public class RedisDistributedLock implements DistributedLock {
    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);

    /**
     * redis 客户端
     */
    private RedisTemplate redisTemplate;

    /**
     * 分布式锁的键值
     */
    private String lockKey;

    /**
     * 锁的超时时间 10s
     */
    private int expireTime = 10 * 1000;

    /**
     * 锁等待，防止线程饥饿
     */
    private int acquireTimeout  = 1 * 1000;

    public RedisDistributedLock(RedisTemplate redisTemplate, String lockKey, int expireTime) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.expireTime = expireTime;
    }

    public RedisDistributedLock(RedisTemplate redisTemplate, String lockKey, int expireTime, int acquireTimeout) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.expireTime = expireTime;
        this.acquireTimeout = acquireTimeout;
    }

    @Override
    public String acquireLock() {
        try {
            long end = System.currentTimeMillis() + acquireTimeout;

            //随机生成一个value
            String requireToken = UUID.randomUUID().toString();
            while (System.currentTimeMillis() < end){
                boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey,requireToken,expireTime, TimeUnit.SECONDS);
                if (result){
                    return requireToken;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }catch (Exception e){
            log.error("acquire lock due to error",e);
        }

        return null;
    }

    @Override
    public boolean releaseLock(String lockLogo) {
        if (lockLogo == null){
            return false;
        }
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script,Long.class);
        redisScript.setScriptText(script);
        List<String> keys = new ArrayList<>();
        keys.add(lockKey);

        Object execute = redisTemplate.execute(redisScript,keys,lockLogo);
        if (execute == null){
            return false;
        }

        return Integer.valueOf(execute.toString()).equals(1);
    }
}
