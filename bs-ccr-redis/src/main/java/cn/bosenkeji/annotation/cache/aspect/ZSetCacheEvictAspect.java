package cn.bosenkeji.annotation.cache.aspect;

import cn.bosenkeji.annotation.cache.ZSetCacheEvict;
import cn.bosenkeji.util.GenerateZSetCache;
import cn.bosenkeji.util.SpelExpressionUtils;
import cn.bosenkeji.vo.ZSetCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
public class ZSetCacheEvictAspect {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(cn.bosenkeji.annotation.cache.ZSetCacheEvict)")
    private void cacheEvict() {}

    @Around(value = "cacheEvict()")
    public Object evictZset(ProceedingJoinPoint point) throws Throwable {

        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Class<?> returnType = method.getReturnType();
        ZSetCacheEvict annotation = method.getAnnotation(ZSetCacheEvict.class);
        String key=annotation.key();
        String value=annotation.value();
        String unless=annotation.unless();
        String score = annotation.score();
        if(score.contains("#")) {
            score = SpelExpressionUtils.parseKey(score, method, point.getArgs());
        }

        Integer scoreInt =Integer.valueOf(score);

        ZSetCache zSetCache=new ZSetCache(key,value,unless,Integer.valueOf(score),false);
        GenerateZSetCache.generationZSetCache(point,method,zSetCache);

        try {
            return point.proceed();
        }finally {

            if(!zSetCache.isNotOperate()) {
                redisTemplate.opsForZSet().removeRangeByScore(key,zSetCache.getScore(),zSetCache.getScore());
            }else {
                Log.info("key:"+key+" delete fail,becuase is unless!!!");
            }
        }
    }
}
