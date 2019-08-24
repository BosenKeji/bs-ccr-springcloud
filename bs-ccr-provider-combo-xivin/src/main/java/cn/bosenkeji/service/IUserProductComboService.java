package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.UserProductCombo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboService {

    int add(UserProductCombo userProductCombo);
    int update(UserProductCombo userProductCombo);
    PageInfo<UserProductCombo> list(int pageNum,int pageSize);
    UserProductCombo get(int id);

    List<UserProductCombo> getByUserId(int userId);
    PageInfo<UserProductCombo> selectUserProductComboByUserTel(int pageNum,int pageSize,String userTel);
    PageInfo<UserProductCombo> selectUserProductComboByUserId(int pageNum,int pageSize,int userId);
    int checkExistByProductIdAndUserId(int productId,int userId);

}