package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.UserMapper;
import cn.bosenkeji.service.UserService;
import cn.bosenkeji.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getByTel(String tel) {
        return userMapper.selectByTel(tel);
    }

    @Override
    public Integer add(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer delete(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer checkExistByUsrename(String username) {
        return userMapper.checkExistByUsername(username);
    }

    @Override
    public Integer checkExistByTel(String tel) {
        return userMapper.checkExistByTel(tel);
    }
}
