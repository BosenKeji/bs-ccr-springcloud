package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboDayByAdminMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.vo.UserProductCombo;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
import cn.bosenkeji.vo.UserProductComboDayByAdminExample;
import cn.bosenkeji.vo.UserProductComboExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public boolean add(UserProductComboDayByAdmin userProductComboDayByAdmin) {
        return userProductComboDayByAdminMapper.insert(userProductComboDayByAdmin);
    }

    @Override
    public boolean update(UserProductComboDayByAdmin userProductComboDayByAdmin) {
        return userProductComboDayByAdminMapper.updateByPrimaryKeySelective(userProductComboDayByAdmin);
    }

    @Override
    public List<UserProductComboDayByAdmin> list() {
        return userProductComboDayByAdminMapper.selectByExample(new UserProductComboDayByAdminExample());
    }

    @Override
    public UserProductComboDayByAdmin get(int id) {
        return userProductComboDayByAdminMapper.selectByPrimaryKey(id);
    }
}
