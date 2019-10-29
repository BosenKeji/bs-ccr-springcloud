package cn.bosenkeji.annotation.cache.aspect;

import cn.bosenkeji.annotation.cache.ZSetCacheable;
import cn.bosenkeji.util.SpelExpressionUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
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
import javax.xml.crypto.dsig.SignatureMethod;
import java.lang.reflect.Method;

@Aspect
@Component
public class ZSetCacheableAspect {

    private final static String RESULT_KEY="result";
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(cn.bosenkeji.annotation.cache.ZSetCacheable)")
    private void cacheable() {}

    @Around(value = "cacheable()")
    public Object cacheZset(ProceedingJoinPoint point) throws Throwable {

        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Class<?> returnType = method.getReturnType();
        ZSetCacheable annotation = method.getAnnotation(ZSetCacheable.class);
        String key=annotation.key();
        String value=annotation.value();
        String unless=annotation.unless();
        //是否不缓存 默认不填则是
        boolean notOperate=false;
        if(key.contains("#")) {
            key = SpelExpressionUtils.parseKey(key,method,point.getArgs());
        }
        if (value.contains("#"))
            value = SpelExpressionUtils.parseKey(value,method,point.getArgs());

        if(unless.contains("#")) {
            notOperate = SpelExpressionUtils.parseResult(unless, method, point, RESULT_KEY);
        }

        Double score = redisTemplate.opsForZSet().score(key, value);

        if(score !=null) {
            return score.intValue();
        }else {
            Integer result=0;
            try {
               return result = (Integer) point.proceed();
            }
            finally {
                if(!notOperate)
                    redisTemplate.opsForZSet().add(key, value, result);
                else {
                    Log.info("key:"+key+" is unless !!!");
                }
            }
        }
    }
}
