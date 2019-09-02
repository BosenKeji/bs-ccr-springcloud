package cn.bosenkeji.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {
        List<String> errorList = new ArrayList<>();
        errorList.add(authException.getMessage());
        Map map = ExceptionUtil.getExceptionMap(401,errorList);
        ExceptionUtil.setResponseParameter(response, HttpServletResponse.SC_UNAUTHORIZED,map);
    }
}