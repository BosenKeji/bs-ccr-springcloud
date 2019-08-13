package cn.bosenkeji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/jwt/user")
    public Object getUser(@RequestParam("token") String token) {

        OAuth2AccessToken accessToken = tokenServices.readAccessToken(token);
        OAuth2Authentication authentication = tokenServices.loadAuthentication(accessToken.getValue());
        Map<String, ?> stringMap = tokenConverter.convertAccessToken(accessToken, authentication);
        return stringMap;
    }
}
