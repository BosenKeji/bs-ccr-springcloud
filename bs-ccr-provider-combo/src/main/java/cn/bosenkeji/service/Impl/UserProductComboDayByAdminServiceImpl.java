package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboDayByAdminMapper;
import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.vo.UserProductComboDay;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.Impl
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
@Service
public class UserProductComboDayByAdminServiceImpl implements IUserProductComboDayByAdminService {

    @Resource
    private UserProductComboDayByAdminMapper userProductComboDayByAdminMapper;
    @Resource
    private UserProductComboDayMapper userProductComboDayMapper;

    @Override
    public boolean add(UserProductComboDay userProductComboDay,int adminId) {

        //新增用户套餐时长
        userProductComboDayMapper.insert(userProductComboDay);
        //新增用户套餐时长操作
        UserProductComboDayByAdmin userProductComboDayByAdmin=new UserProductComboDayByAdmin();
        userProductComboDayByAdmin.setAdminId(adminId);
        userProductComboDayByAdmin.setUserProductComboDayId(userProductComboDay.getId());
        return userProductComboDayByAdminMapper.insert(userProductComboDayByAdmin);

    }

    @Override
    public boolean update(UserProductComboDayByAdmin userProductComboDayByAdmin) {
        return userProductComboDayByAdminMapper.updateByPrimaryKeySelective(userProductComboDayByAdmin);
    }

    @Override
    public PageInfo<UserProductComboDayByAdmin> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo(userProductComboDayByAdminMapper.findAll());
    }

    @Override
    public Optional<UserProductComboDayByAdmin> get(int id) {
        return Optional.ofNullable(userProductComboDayByAdminMapper.selectByPrimaryKey(id));
    }
}
