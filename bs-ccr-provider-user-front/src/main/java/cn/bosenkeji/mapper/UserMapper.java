package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.User;

/**
 * @Author CAJR
 * @create 2019/7/17 10:34
 */

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    User selectByUsername(String  username);
    User selectByTel(String  tel);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    int checkExistByUsername(String username);
}