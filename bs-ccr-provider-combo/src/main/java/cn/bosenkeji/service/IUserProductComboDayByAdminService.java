package cn.bosenkeji.service;

import cn.bosenkeji.vo.UserProductCombo;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;

import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboDayByAdminService {

    boolean add(UserProductComboDayByAdmin userProductComboDayByAdmin);
    boolean update(UserProductComboDayByAdmin userProductComboDayByAdmin);
    List<UserProductComboDayByAdmin> list();
    UserProductComboDayByAdmin get(int id);
}
