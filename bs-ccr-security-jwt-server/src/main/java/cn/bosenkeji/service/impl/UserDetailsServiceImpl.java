package cn.bosenkeji.service.impl;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.AdminEnum;
import cn.bosenkeji.exception.enums.UserEnum;
import cn.bosenkeji.service.AdminService;
import cn.bosenkeji.service.UserService;
import cn.bosenkeji.vo.Admin;
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

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<User> user = userService.getByUsername(username);

        if (user.isPresent() == true){
            return new CustomUserDetailsImpl(user.get());
        } else {
            final Admin admin = adminService.selectByAccount(username).orElseThrow(() -> new NotFoundException(UserEnum.NAME));

            return new CustomAdminDetailsImpl(admin);
        }

    }
}
