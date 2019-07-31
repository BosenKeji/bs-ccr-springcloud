package cn.bosenkeji.service;


import cn.bosenkeji.vo.User;

import java.util.Optional;

public interface UserService {

    Optional<User> get(int id);
    Optional<User> getByUsername(String username);
    Optional<Integer> add(User user);
    Optional<Integer> update(User user);
    Optional<Integer> delete(int id);
    Optional<Integer> checkExistByUsrename(String username);



}
