package cn.bosenkeji.service.impl;

import cn.bosenkeji.enums.exception.user.UserEnum;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName UserDetailsServiceImp
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomJdbcUserDetailsService userDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final  Optional<User> user = userDetailsService.getOneUserByTel(username);

        if (user.isPresent()) {
            return new CustomUserDetailsImpl(user.get());
        } else {
            final Admin admin = userDetailsService.getOneAdminByAccount(username).orElseThrow(()-> new UsernameNotFoundException(username));
            return new CustomAdminDetailsImpl(admin);
        }
    }
}
