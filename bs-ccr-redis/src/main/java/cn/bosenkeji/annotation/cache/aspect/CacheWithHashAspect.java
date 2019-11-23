package cn.bosenkeji.annotation.cache.aspect;

import cn.bosenkeji.annotation.cache.CacheWithHash;
import cn.bosenkeji.interfaces.HashOperation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.CachedExpressionEvaluator;
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
@SuppressWarnings("all")
public class CacheWithHashAspect{
    
    private static final String RESULT="result";

    @Pointcut("@annotation(cn.bosenkeji.annotation.cache.CacheWithHash)")
    private  void dealCacheWithHash() {}



    @Resource
    private RedisTemplate<String,String> redisTemplate;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Around(value = "dealCacheWithHash()")
    public Object saveHash(ProceedingJoinPoint point) throws Throwable {

        //redisTemplate.opsForHash()
        try {
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            Class<?> returnType = method.getReturnType();
            CacheWithHash annotation = method.getAnnotation(CacheWithHash.class);
            String key = annotation.key();
            String name = annotation.name();
            String operation=annotation.operation();
            String value = annotation.value();
            String unlessStr=annotation.unless();
            boolean unless = false;
            if (StringUtils.isBlank(name))
                throw new Exception("name can not null");

            if (value.contains("#"))
                value = parseKey(value, method, point.getArgs());
            if (key.contains("#"))
                key = parseKey(key, method, point.getArgs());
            if(unlessStr.contains("#"))
                unless=parseResult(unlessStr,method,point);

            if (HashOperation.GET.equals(operation)) {
                return hashGet(point,name,key,returnType,unless);
            }
            else if(HashOperation.SET.equals(operation)) {
                return hashSet(point,name,key,value,unless);
            }
            else if(HashOperation.REMOVE.equals(operation)) {
                return hashRemove(point,name,key,unless);
            }
            else if(HashOperation.DELETE.equals(operation)) {
                return hashDelete(point,name,unless);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return point.proceed();

    }

    /**
     * 删除整个hash方法
     * @param point
     * @param name
     * @return
     * @throws Throwable
     */
    private Object hashDelete(ProceedingJoinPoint point, String name,boolean unless) throws Throwable {
        try {
            return point.proceed();
        }finally {
            redisTemplate.delete(name);
        }
    }

    /**
     * 移除 hash 的某一个元素方法
     * @param point
     * @param name
     * @param key
     * @return
     * @throws Throwable
     */
    private Object hashRemove(ProceedingJoinPoint point, String name, String key,boolean unless) throws Throwable {
        try {
            return point.proceed();
        }finally {
            redisTemplate.opsForHash().delete(name,key);
        }
    }

    /**
     *  给 hash put 一个元素
     * @param point
     * @param name
     * @param key
     * @param value
     * @return
     * @throws Throwable
     */
    private Object hashSet(ProceedingJoinPoint point, String name, String key,String value,boolean unless) throws Throwable {
        try {
            return point.proceed();
        }finally {
            if(!unless)
                redisTemplate.opsForHash().put(name,key,value);
        }
    }

    /**
     * 从 hash 获取元素，不存在就把方法的返回值 设进去
     * @param point
     * @param name
     * @param key
     * @return
     * @throws Throwable
     */
    private Object hashGet(ProceedingJoinPoint point,String name,String key,Class<?> returnType,boolean unless) throws Throwable {

        Boolean isHasHash = redisTemplate.opsForHash().hasKey(name, key);
        Class<? extends Class> aClass = returnType.getClass();
        if (isHasHash) {
            Object o = redisTemplate.opsForHash().get(name, key);
            String s = JSON.toJSONString(o);
            Object result = JSON.parseObject(s, returnType);
            return result;
        } else {

            Object result=null;
            try {
                return result=point.proceed();
            }finally {
                //if(unless)
                //boolean isOperate=Boolean.parseBoolean(unless);
                System.out.println(unless+"unless === "+unless);
                if(!unless) {
                    redisTemplate.opsForHash().put(name, key, String.valueOf(result));
                    Log.info("hash put success!!!----" + key + ",value=" + result);
                }else {
                    Log.info(key+" the value is null!!!");
                }
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
    private static String parseKey(String key,Method method,Object[] args) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();

        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = u.getParameterNames(method);
        Class<?>[] parameterTypes = method.getParameterTypes();

        for (Class<?> parameterType : parameterTypes) {
            context.setVariable(parameterType.getName(),parameterType);
        }

        for(int i=0;i<parameterNames.length;i++) {
            context.setVariable(parameterNames[i],args[i]);
        }


        return parser.parseExpression(key).getValue(context,String.class);
    }

    /**
     * 解析 unless 的 #result  返回boolean 类型
     * @param unless
     * @param method
     * @param point
     * @return
     * @throws Throwable
     */
    private static boolean parseResult(String unless,Method method,ProceedingJoinPoint point) throws Throwable {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();

        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object result = point.proceed();
        context.setVariable(RESULT,result);
        return parser.parseExpression(unless).getValue(context,Boolean.class);
        

    }

    public static void main(String[] args) {
        String str = "1==null";
        Object[] objects=new Object[5];
        objects[1]="1";
        String s = parseKey(str, null, objects);
        System.out.println("s = " + s);
    }
}
