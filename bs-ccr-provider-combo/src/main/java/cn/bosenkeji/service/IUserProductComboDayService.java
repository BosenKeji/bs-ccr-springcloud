package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.UserProductComboDay;
import com.github.pagehelper.PageInfo;

import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboDayService {

    boolean add(UserProductComboDay userProductComboDay);
    boolean update(UserProductComboDay userProductComboDay);
    PageInfo<UserProductComboDay> list(int pageNum,int pageSize);
    PageInfo<UserProductComboDay> selectByUserTel(String tel,int pageNum,int pageSize);

    PageInfo<UserProductComboDay> selectByUserProductComboId(int userProductComboId,int pageNum,int pageSize);
    Optional<UserProductComboDay> get(int id);
}
