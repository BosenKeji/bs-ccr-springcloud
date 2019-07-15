package cn.bosenkeji.service;

import cn.bosenkeji.vo.UserProductComboDay;

import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboDayService {

    boolean add(UserProductComboDay userProductComboDay);
    boolean update(UserProductComboDay userProductComboDay);
    List<UserProductComboDay> list();
    UserProductComboDay get(int id);
}
