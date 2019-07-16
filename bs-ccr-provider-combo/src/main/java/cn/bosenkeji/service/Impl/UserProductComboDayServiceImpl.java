package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.IUserProductComboDayService;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.vo.UserProductCombo;
import cn.bosenkeji.vo.UserProductComboDay;
import cn.bosenkeji.vo.UserProductComboDayExample;
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
public class UserProductComboDayServiceImpl implements IUserProductComboDayService {

    @Resource
    private UserProductComboDayMapper userProductComboDayMapper;

    @Override
    public boolean add(UserProductComboDay userProductComboDay) {
        return userProductComboDayMapper.insert(userProductComboDay);
    }

    @Override
    public boolean update(UserProductComboDay userProductComboDay) {
        return userProductComboDayMapper.updateByPrimaryKeySelective(userProductComboDay);
    }

    @Override
    public List<UserProductComboDay> list() {
        return userProductComboDayMapper.selectByExample(new UserProductComboDayExample());
    }

    @Override
    public UserProductComboDay get(int id) {
        return userProductComboDayMapper.selectByPrimaryKey(id);
    }
}
