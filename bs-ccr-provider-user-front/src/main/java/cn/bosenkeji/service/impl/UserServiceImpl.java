package cn.bosenkeji.service.impl;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.UserMapper;
import cn.bosenkeji.service.UserService;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Cacheable(value = RedisInterface.USER_REDIS_ID_KEY,key = "#id",unless = "#result==null")
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

    @Cacheable(value = RedisInterface.USER_REDIS_USERNAME_KEY,key = "#username",unless = "#result < 1")
    @Override
    public Integer getIdByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        if(user!=null) {
            return user.getId();
        }else
            return 0;
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

    //同步缓存
    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.USER_REDIS_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true)
            }
    )
    @Override
    public void evictUser(int id) {
        System.out.println("update caceh userId"+id);
    }

    @Override
    public Integer checkExistById(int id) {
        return userMapper.checkExistById(id);
    }
}
