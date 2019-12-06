package cn.bosenkeji.controller;

import cn.bosenkeji.service.RoleService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName JwtController
 * @Description TODO
 * @Author hjh
 * @Date 2019-08-13 11:00
 * @Version 1.0
 */
@RestController
public class JwtController {

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private JwtAccessTokenConverter tokenConverter;

    @Autowired
    private RoleService roleService;


    @GetMapping("/jwt/user")
    public Object getUser(@RequestParam("token") String token) {

        OAuth2AccessToken accessToken = tokenServices.readAccessToken(token);
        OAuth2Authentication authentication = tokenServices.loadAuthentication(accessToken.getValue());
        Map<String, ?> stringMap = tokenConverter.convertAccessToken(accessToken, authentication);
        return stringMap;
    }

//    @GetMapping("/test")
//    public String test() {
//
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            executorService.execute(() -> {
//                RestTemplate restTemplate = new RestTemplate();
//                HttpHeaders headers = new HttpHeaders();
//
//                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//                String s = new String(Base64.encodeBase64("123:123".getBytes()));
//                headers.set("Authorization","Basic " + s);
//
//                MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
//                map.add("grant_type","password");
//                map.add("username","13560509160");
//                map.add("password","123");
//
//                HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<>(map,headers);
//
//                String s1 = restTemplate.postForObject("http://127.0.0.1:8100/oauth/token", entity, String.class);
//                System.out.println(s1);
//            });
//        }
//        return "end";
//    }


}
