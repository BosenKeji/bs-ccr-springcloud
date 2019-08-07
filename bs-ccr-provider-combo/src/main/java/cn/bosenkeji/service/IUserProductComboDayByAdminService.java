package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboDayByAdminService {

    int add(UserProductComboDay userProductComboDay,int adminId);
    int update(UserProductComboDayByAdmin userProductComboDayByAdmin);
    UserProductComboDayByAdmin get(int id);

}
