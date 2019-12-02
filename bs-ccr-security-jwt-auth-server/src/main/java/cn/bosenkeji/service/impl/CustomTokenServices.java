package cn.bosenkeji.service.impl;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

public class CustomTokenServices extends DefaultTokenServices {

    @Override
    public synchronized OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        return super.createAccessToken(authentication);
    }
}
