package cn.bosenkeji.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

public class SpelExpressionUtils {

    /**
     * parseKey for SPEL Expression
     * @param key
     * @param method
     * @param args
     * @return
     */
    public static String parseKey(String key, Method method, Object[] args) {
        LocalVariableTableParameterNameDiscoverer u = getParameterName();

        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        /**
         * 获取 方法传入参数 以 key value形式保存
         */
        try {
            String[] parameterNames = u.getParameterNames(method);
            for(int i=0;i<parameterNames.length;i++) {
                context.setVariable(parameterNames[i],args[i]);
            }
        }catch (Exception e) {
            //不处理
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
    public static boolean parseResult(String unless, Method method, ProceedingJoinPoint point,String resultKey) throws Throwable {
        LocalVariableTableParameterNameDiscoverer u = getParameterName();

        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        try {
            Object result = point.proceed();
            context.setVariable(resultKey, result);
        }
        catch (Exception e) {
            //不处理
        }
        return parser.parseExpression(unless).getValue(context,Boolean.class);


    }

    /**
     * new LocalVariableTableParameterNameDiscoverer()
     * @return
     */
    private static LocalVariableTableParameterNameDiscoverer getParameterName() {
        return new LocalVariableTableParameterNameDiscoverer();
    }

}
