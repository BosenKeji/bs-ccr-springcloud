package cn.bosenkeji.service;

import cn.bosenkeji.vo.User;

public interface AuthService {

    User register(User userToAdd);
    String login(String username,String password);
    String refresh(String oldToken);
}
