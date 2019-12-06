package cn.bosenkeji.service.impl;

import cn.bosenkeji.utils.RedisLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;


public class CustomTokenServices extends DefaultTokenServices {


    private RedisLockUtil redisLockUtil;

    public CustomTokenServices() {
    }

    public CustomTokenServices(RedisLockUtil redisLockUtil) {
        this.redisLockUtil = redisLockUtil;
    }

    @Override
    public  OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {

        OAuth2AccessToken accessToken = null;
        Object principal = authentication.getUserAuthentication().getPrincipal();
        UserDetails userDetails;
        if (principal instanceof CustomAdminDetailsImpl) {
           userDetails = (CustomAdminDetailsImpl) principal;
        } else {
            userDetails = (CustomUserDetailsImpl) principal;
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
        } finally {
            while (true) {
                boolean unLock = redisLockUtil.unLock( username, password);
                if (unLock) {
                    break;
                }
            }
        }

       return accessToken;
    }
}
