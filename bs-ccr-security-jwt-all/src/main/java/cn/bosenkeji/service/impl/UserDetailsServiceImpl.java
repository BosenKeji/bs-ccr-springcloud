package cn.bosenkeji.service.impl;

import cn.bosenkeji.enums.exception.user.UserEnum;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.service.IAdminClientService;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
@EnableFeignClients("cn.bosenkeji.service")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserClientService iUserClientService;

    @Autowired
    private IAdminClientService iAdminClientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<User> user = Optional.ofNullable(iUserClientService.getOneUserByTel(username));

        if (user.isPresent() == true){
            return new CustomUserDetailsImpl(user.get());
        } else {
            final Admin admin = iAdminClientService.selectByAccount(username).orElseThrow(() -> new NotFoundException(UserEnum.NAME));

            return new CustomAdminDetailsImpl(admin);
        }

    }
}
