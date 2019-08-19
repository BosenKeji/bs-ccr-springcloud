package cn.bosenkeji.service;

import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName AdminService
 * @Description 货币服务接口
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
public interface AdminService {
    List<Admin> list();

    PageInfo listByPage(int pageNum, int pageSize);

    Optional<Admin> get(int id);

    Optional<Admin> selectByAccount(String account);

    Optional<Integer> add(Admin admin);

    Optional<Integer> update(Admin admin);

    Optional<Integer> delete(int id);

    Map<Integer,Admin> selectByPrimaryKeys(List<Integer> ids);

    Optional<Integer> resetPassword(int id,String password);
}
