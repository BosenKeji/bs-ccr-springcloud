package cn.bosenkeji.annotation.cache.aspect;

import cn.bosenkeji.annotation.cache.BatchCacheRemove;
import cn.bosenkeji.util.SpelExpressionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
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
import java.lang.reflect.Method;
import java.util.Set;

@Aspect
@Component
public class CacheRemoveAspect {

    private final static String RESULT_KEY="result";

    @Resource
    private RedisTemplate redisTemplate;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(cn.bosenkeji.annotation.cache.BatchCacheRemove)")
    public Object remove(ProceedingJoinPoint point) throws Throwable {

        //执行操作，注意 只执行一次
        Object proceed = point.proceed();
        //Log.info("proceed is "+proceed);
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        BatchCacheRemove annotation = method.getAnnotation(BatchCacheRemove.class);
        String[] keys = annotation.value();
        String unless = annotation.unless();
        String condition = annotation.condition();
        boolean isNotCache = false;
        boolean isCache = true;


        if (unless.contains("#")) {
            isNotCache = SpelExpressionUtils.parseResult(unless, method, proceed, RESULT_KEY, point.getArgs());
        }
        if (condition.contains("#")) {
            isCache = SpelExpressionUtils.parseResult(condition, method, proceed, RESULT_KEY, point.getArgs());
        }

        //如果 不执行清空缓存的情况
        if (isNotCache) {
            Log.info("isNotCache is true");
            return proceed;
        }
        if (!isCache) {
            Log.info("isCache is false");
            return proceed;
        }

        try {

            return proceed;

        }finally {


            for (String key : keys) {
                if (key.contains("#")) {
                    key = SpelExpressionUtils.parseKey(key, method, point.getArgs());
                }
                Set deleteKey = redisTemplate.keys(key + "*");
                redisTemplate.delete(deleteKey);

                Log.info("cache key:" + key + " deleted");
            }

        }
    }


    /**
     * parseKey for SPEL Expression
     * @param key
     * @param method
     * @param args
     * @return
     */
    /*private String parseKey(String key,Method method,Object[] args) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = u.getParameterNames(method);
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();


        for(int i=0;i<parameterNames.length;i++) {
            if(null != args[i])
                context.setVariable(parameterNames[i],args[i]);
        }

        return parser.parseExpression(key).getValue(context,String.class);
    }*/
}
