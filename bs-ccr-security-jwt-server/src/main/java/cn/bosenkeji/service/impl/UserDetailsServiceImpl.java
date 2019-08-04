package cn.bosenkeji.service.impl;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.UserEnum;
import cn.bosenkeji.service.UserService;
import cn.bosenkeji.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userService.getByUsername(username).orElseThrow(() -> new NotFoundException(UserEnum.NAME));

        return new CustomUserDetailsImpl(user);
    }
}
