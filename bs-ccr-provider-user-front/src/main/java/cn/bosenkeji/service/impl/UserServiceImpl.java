package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.UserMapper;
import cn.bosenkeji.service.UserService;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
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

    @Override
    public Integer updateBinding(int id, int isBinding) {
        return userMapper.updateBinding(id,isBinding);
    }

    @Override
    public Integer updatePasswordByTel(String tel, String password) {
        return userMapper.updatePasswordByTel(tel,password);
    }

    @Override
    public Map<Integer, User> getByIds(List<Integer> ids) {
        return userMapper.getByIds(ids);
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public PageInfo<User> listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(this.list());
    }
}
