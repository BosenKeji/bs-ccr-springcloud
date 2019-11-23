package cn.bosenkeji.util;

import cn.bosenkeji.vo.ZSetCache;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public class GenerateZSetCache {

    private static final String POUND="#";
    private static final String resultKey="result";

    public static ZSetCache generationZSetCache(ProceedingJoinPoint point, Method method, ZSetCache cache, Object proceed) throws Throwable {

        cache.setKey(cache.getKey().contains(POUND)?SpelExpressionUtils.parseKey(cache.getKey(),method,point.getArgs()):cache.getKey());
        cache.setValue(cache.getValue().contains(POUND)?SpelExpressionUtils.parseKey(cache.getValue(),method,point.getArgs()):cache.getValue());
        cache.setNotOperate(cache.getUnless().contains(POUND)?SpelExpressionUtils.parseResult(cache.getUnless(),method, proceed,resultKey,point.getArgs()):false);

        return cache;
    }

    public static ZSetCache generationZSetCacheWithScore(ProceedingJoinPoint point, Method method,ZSetCache cache, Object proceed) throws Throwable {

        generationZSetCache(point,method,cache,proceed);

        return cache;
    }
}
