package cn.bosenkeji.service;


import cn.bosenkeji.vo.User;

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



}
