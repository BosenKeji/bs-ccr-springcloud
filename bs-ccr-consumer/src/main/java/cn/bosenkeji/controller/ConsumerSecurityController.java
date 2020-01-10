package cn.bosenkeji.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpServletRequest;

/**
 * ConsumerSecurityController
 *
 * @author hjh
 * @date 2020-01-10
 */
@RestController
@Api(tags = "jwt-security 相关接口", value = "提供安全相关接口的 Rest API")
@Validated
public class ConsumerSecurityController {

    @Autowired
    private RestOperations restTemplate;

    private static final String HOST = "http://bs-ccr-security-jwt-auth-server";

    private String url(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return HOST + requestUri;
    }

    private HttpHeaders authorizationHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String authorization = request.getHeader("Authorization");
        headers.set("Authorization",authorization);

        return headers;
    }

    /**
     * 获取token
     * @param map 请求参数
     * @param request request
     * @return token信息
     */
    @ApiOperation(value = "获取token接口", httpMethod = "POST",nickname = "postAccessToken")
    @PostMapping("/oauth/token")
    Object postAccessToken(@RequestParam LinkedMultiValueMap<String, String> map, HttpServletRequest request){
        String url = url(request);
        HttpHeaders headers = authorizationHeaders(request);
        HttpEntity<LinkedMultiValueMap> entity = new HttpEntity<>(map,headers);
        return restTemplate.postForObject(url,entity,Object.class);
    }

    /**
     * 检查token
     * @param token token
     * @param request request
     * @return token相关信息
     */
    @ApiOperation(value = "检查token列表接口", httpMethod = "GET",nickname = "checkToken")
    @GetMapping("/oauth/check_token")
    Object checkToken(@RequestParam("token") String token,HttpServletRequest request){
        String url = url(request) + "?token=" + token;
        HttpHeaders headers = authorizationHeaders(request);
        HttpEntity<LinkedMultiValueMap> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
    }


}
