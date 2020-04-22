package cn.bosenkeji.service.impl;

import cn.bosenkeji.utils.RedisLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


public class CustomTokenServices extends DefaultTokenServices {


    private RedisLockUtil redisLockUtil;

    public CustomTokenServices() {
    }

    public CustomTokenServices(RedisLockUtil redisLockUtil) {
        this.redisLockUtil = redisLockUtil;
    }

    @Override
    public  OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        DefaultOAuth2AccessToken oAuth2AccessToken;
        OAuth2AccessToken accessToken;
        Object principal = authentication.getUserAuthentication().getPrincipal();
        UserDetails userDetails;
        CustomUserDetailsImpl user;
        CustomAdminDetailsImpl admin;
        //附加信息map
        Map<String, Object> userInfoMap = new HashMap<>();
        if (principal instanceof CustomAdminDetailsImpl) {
            userDetails = (CustomAdminDetailsImpl) principal;
            admin = (CustomAdminDetailsImpl) principal;
            userInfoMap.put("user_id",admin.getId());
            userInfoMap.put("user_name",userDetails.getUsername());
        } else {
            userDetails = (CustomUserDetailsImpl) principal;
            user = (CustomUserDetailsImpl) principal;
            userInfoMap.put("user_id",user.getId());
            userInfoMap.put("user_name",user.getUsername());
        }
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        try {
            while(true) {
                boolean lock = redisLockUtil.lock(username, password, 60);
                if (lock) {
                    break;
                }
            }
            accessToken = super.createAccessToken(authentication);
            oAuth2AccessToken = (DefaultOAuth2AccessToken)accessToken;
            oAuth2AccessToken.setAdditionalInformation(userInfoMap);
        } finally {
            while (true) {
                boolean unLock = redisLockUtil.unLock( username, password);
                if (unLock) {
                    break;
                }
            }
        }

        return oAuth2AccessToken;
    }
}
