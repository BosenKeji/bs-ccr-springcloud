package cn.bosenkeji.annotation.resolver;

import cn.bosenkeji.service.impl.CustomUserDetailsImpl;
import cn.bosenkeji.annotation.TokenUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

/**
 * TODO 从JWT获取UserId注解解析器
 * @Author CAJR
 * @create 2019/8/28 15:07
 */
@Component
public class TokenUserAnnotationResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(TokenUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        int result =0;
        Annotation[] methodAnnotations = methodParameter.getParameterAnnotations();
        if (methodAnnotations.length != 0){
            for (Annotation a : methodAnnotations) {
                if (a instanceof TokenUser){
                    CustomUserDetailsImpl customUserDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    result = customUserDetails.getId();
                    break;
                }
            }
        }
        return result;
    }
}
