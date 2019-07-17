package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.service.IUserProductComboDayService;
import cn.bosenkeji.vo.UserProductComboDay;
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
public class UserProductComboDayServiceImpl implements IUserProductComboDayService {

    @Resource
    private UserProductComboDayMapper userProductComboDayMapper;

    @Override
    public boolean add(UserProductComboDay userProductComboDay) {
        //添加缓存
        return userProductComboDayMapper.insert(userProductComboDay);
    }

    @Override
    public boolean update(UserProductComboDay userProductComboDay) {
        return userProductComboDayMapper.updateByPrimaryKeySelective(userProductComboDay);
    }

    @Override
    public PageInfo<UserProductComboDay> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(userProductComboDayMapper.findAll());
    }

    @Override
    public Optional<UserProductComboDay> get(int id) {
        return Optional.ofNullable(userProductComboDayMapper.selectByPrimaryKey(id));
    }
}
