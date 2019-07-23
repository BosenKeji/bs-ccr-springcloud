package cn.bosenkeji.service;

import cn.bosenkeji.vo.UserProductCombo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboService {

    Optional<Integer> add(UserProductCombo userProductCombo);
    Optional<Integer> update(UserProductCombo userProductCombo);
    PageInfo<UserProductCombo> list(int pageNum,int pageSize);
    Optional<UserProductCombo> get(int id);

    List<UserProductCombo> getByUserId(int userId);
    PageInfo<UserProductCombo> selectUserProductComboByUserTel(int pageNum,int pageSize,String userTel);

}
