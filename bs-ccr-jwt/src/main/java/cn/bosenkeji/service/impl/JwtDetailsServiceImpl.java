package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.UserMapper;
import cn.bosenkeji.vo.JwtUser;
import cn.bosenkeji.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName JwtDetailsServiceImpl
 * @Description TODO
 * @Author hjh
 * @Date 2019-08-03 19:37
 * @Version 1.0
 */

@Service
public class JwtDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User  user = userMapper.findUserByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", name));
        }
        else {
            List<String> authorities = new ArrayList<>();
            authorities.add("admin");
            JwtUser jwtUser = new JwtUser(user.getId(),user.getUsername(),user.getPassword(),
                    user.getTel(),user.getStatus(),mapToGrantedAuthorities(authorities));
            return jwtUser;
        }
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
