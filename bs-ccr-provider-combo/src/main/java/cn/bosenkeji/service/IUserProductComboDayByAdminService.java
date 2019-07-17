package cn.bosenkeji.service;

import cn.bosenkeji.vo.UserProductCombo;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
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

    boolean add(UserProductComboDayByAdmin userProductComboDayByAdmin);
    boolean update(UserProductComboDayByAdmin userProductComboDayByAdmin);
    PageInfo<UserProductComboDayByAdmin> list(int pageNum,int pageSize);
    Optional<UserProductComboDayByAdmin> get(int id);
}
