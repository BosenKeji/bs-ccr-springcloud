package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.UserMapper;
import cn.bosenkeji.service.UserService;
import cn.bosenkeji.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Optional<User> get(int id) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(id));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(userMapper.selectByUsername(username));
    }

    @Override
    public Optional<Integer> add(User user) {
        return Optional.ofNullable(userMapper.insertSelective(user));
    }

    @Override
    public Optional<Integer> update(User user) {
        return Optional.ofNullable(userMapper.updateByPrimaryKeySelective(user));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(userMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkExistByUsrename(String username) {
        return Optional.ofNullable(userMapper.checkExistByUsername(username));
    }
}
