package cn.bosenkeji.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

class ExceptionUtil {

    static Map<String, Object> getExceptionMap(Integer status, List<String> errors) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", String.valueOf(new Timestamp(new Date().getTime())));
        map.put("status", "401");
        map.put("errors", errors);
        return map;
    }

    static void setResponseParameter(HttpServletResponse response, Integer status, Map body) throws ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
        } catch (Exception e) {
            throw new ServletException();
        }
    }


}
