package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;

import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboDayByAdminService {

    Optional<Integer> add(UserProductComboDay userProductComboDay,int adminId);
    Optional<Integer> update(UserProductComboDayByAdmin userProductComboDayByAdmin);
    Optional<UserProductComboDayByAdmin> get(int id);

}
