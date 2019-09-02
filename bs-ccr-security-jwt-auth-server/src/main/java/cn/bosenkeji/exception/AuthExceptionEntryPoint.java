package cn.bosenkeji.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;


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