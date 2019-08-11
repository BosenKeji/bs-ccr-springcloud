package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.UserProductComboDay;
import com.github.pagehelper.PageInfo;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
public interface IUserProductComboDayService {

    int add(UserProductComboDay userProductComboDay);
    int update(UserProductComboDay userProductComboDay);
    PageInfo<UserProductComboDay> list(int pageNum,int pageSize);
    PageInfo<UserProductComboDay> selectByUserTel(String tel,int pageNum,int pageSize);

    PageInfo<UserProductComboDay> selectByUserProductComboId(int userProductComboId,int pageNum,int pageSize);
    UserProductComboDay get(int id);
}
