package cn.bosenkeji.annotation.cache.aspect;

import cn.bosenkeji.annotation.cache.MyCacheRemove;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Documented;
import java.lang.reflect.Method;
import java.util.Set;

@Aspect
@Component
public class CacheRemoveAspect {

    @Resource
    private RedisTemplate redisTemplate;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @AfterReturning("@annotation(cn.bosenkeji.annotation.cache.MyCacheRemove)")
    public void remove(JoinPoint point) {

        Method method = ((MethodSignature) point.getSignature()).getMethod();
        MyCacheRemove annotation = method.getAnnotation(MyCacheRemove.class);
        String[] keys=annotation.value();
        for (String key : keys) {
            if(key.contains("#"))
                key = parseKey(key,method,point.getArgs());
            Set deleteKey = redisTemplate.keys(key);
            redisTemplate.delete(deleteKey);

            Log.info("cache key:" +key+" deleted");
        }


    }

    /**
     * parseKey for SPEL Expression
     * @param key
     * @param method
     * @param args
     * @return
     */
    private String parseKey(String key,Method method,Object[] args) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = u.getParameterNames(method);
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for(int i=0;i<parameterNames.length;i++) {
            context.setVariable(parameterNames[i],args[i]);
        }

        return parser.parseExpression(key).getValue(context,String.class);
    }
}
