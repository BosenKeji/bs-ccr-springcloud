package cn.bosenkeji.controller;

import cn.bosenkeji.mapper.UserMapper;
import cn.bosenkeji.service.AuthService;
import cn.bosenkeji.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author hjh
 * @Date 2019-08-03 20:08
 * @Version 1.0
 */

@RestController
@RequestMapping
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    @Value("${jwt.header}")
    private String tokenHeader;


    @GetMapping("/")
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody User user) throws ArithmeticException{
        final String token = authService.login(user.getUsername(), user.getPassword());

        // Return the token
        return ResponseEntity.ok(token);
    }
}
