package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import com.github.pagehelper.PageInfo;

import java.util.List;
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
    PageInfo<UserProductComboDayByAdmin> list(int pageNum,int pageSize);
    Optional<UserProductComboDayByAdmin> get(int id);
    List<UserProductComboDayByAdmin> getByUserProductComboId(int userProductComboId);
    PageInfo<UserProductComboDayByAdmin> getByUserTel(String userProductComboId,int pageNum,int pageSize);

}
