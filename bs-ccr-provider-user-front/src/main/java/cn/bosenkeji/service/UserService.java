package cn.bosenkeji.service;


import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    User get(int id);
    User getByUsername(String username);
    User getByTel(String tel);
    Integer add(User user);
    Integer updateByPrimaryKeySelective(User user);
    Integer delete(int id);
    Integer checkExistByUsrename(String username);
    Integer checkExistByTel(String tel);
    Integer updateBinding(int id,int isBinding);
    Integer updatePasswordByTel(String tel,String password);
    Map<Integer,User> getByIds(List<Integer> ids);

    List<User> list();
    PageInfo<User> listByPage(int pageNum,int pageSize);

    void evictUser(int id);

    Integer getIdByUsername(String username);

    Integer checkExistById(int id);

    Integer updateStatusById(Integer id, Integer status);



}
