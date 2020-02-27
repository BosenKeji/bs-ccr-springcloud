package cn.bosenkeji.service.impl;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CAJR
 * @date 2020/2/27 7:31 下午
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getPrincipal();
        DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
        CustomUserDetailsImpl user;
        CustomAdminDetailsImpl admin;
        Map<String, Object> userInfoMap = new HashMap<>();

        if ( principal != null) {
            if ( principal instanceof CustomUserDetailsImpl) {
                user = (CustomUserDetailsImpl) principal;
                userInfoMap.put("user_id", user.getId());
                userInfoMap.put("user_name", user.getUsername());
            } else if (principal instanceof CustomAdminDetailsImpl) {
                admin = (CustomAdminDetailsImpl) principal;
                userInfoMap.put("user_id", admin.getId());
                userInfoMap.put("user_name", admin.getUsername());
            }
        }

        oAuth2AccessToken.setAdditionalInformation(userInfoMap);
        return oAuth2AccessToken;
    }
}
