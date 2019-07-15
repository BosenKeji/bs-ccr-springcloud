package cn.bosenkeji.service;

import cn.bosenkeji.vo.UserProductCombo;

import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboService {

    boolean add(UserProductCombo userProductCombo);
    boolean update(UserProductCombo userProductCombo);
    List<UserProductCombo> list();
    UserProductCombo get(int id);
}
