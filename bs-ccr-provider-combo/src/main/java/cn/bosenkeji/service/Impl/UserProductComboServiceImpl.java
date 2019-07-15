package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.vo.UserProductCombo;
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
public class UserProductComboServiceImpl implements IUserProductComboService {

    @Resource
    private UserProductComboMapper userProductComboMapper;

    @Override
    public boolean add(UserProductCombo userProductCombo) {
        return userProductComboMapper.insert(userProductCombo);
    }

    @Override
    public boolean update(UserProductCombo userProductCombo) {
        return userProductComboMapper.updateByPrimaryKeySelective(userProductCombo);
    }

    @Override
    public List<UserProductCombo> list() {
        return userProductComboMapper.selectByExample(new UserProductComboExample());
    }

    @Override
    public UserProductCombo get(int id) {
        return userProductComboMapper.selectByPrimaryKey(id);
    }
}
