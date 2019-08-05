package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboDayByAdminMapper;
import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.mapper.UserProductComboRedisTemplate;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private UserProductComboRedisTemplate userProductComboRedisTemplate;

    @Resource
    private IUserClientService iUserClientService;


    @Override
    public Optional<Integer> add(UserProductComboDay userProductComboDay,int adminId) {

        //新增用户套餐时长
        userProductComboDayMapper.insert(userProductComboDay);
        //添加缓存
        int id = userProductComboDay.getUserProductComboId();

        //设置增加的有效时间
        Long expire = userProductComboRedisTemplate.getExpire(id);
        if(expire>0) {
            userProductComboRedisTemplate.setExpire(id,expire+userProductComboDay.getNumber());
        }

        //新增用户套餐时长操作
        UserProductComboDayByAdmin userProductComboDayByAdmin=new UserProductComboDayByAdmin();
        userProductComboDayByAdmin.setAdminId(adminId);
        userProductComboDayByAdmin.setUserProductComboDayId(userProductComboDay.getId());

        return Optional.ofNullable(userProductComboDayByAdminMapper.insert(userProductComboDayByAdmin));

    }

    @Override
    public Optional<Integer> update(UserProductComboDayByAdmin userProductComboDayByAdmin) {
        return Optional.ofNullable(userProductComboDayByAdminMapper.updateByPrimaryKeySelective(userProductComboDayByAdmin));
    }



    @Override
    public Optional<UserProductComboDayByAdmin> get(int id) {
        return Optional.ofNullable(userProductComboDayByAdminMapper.selectByPrimaryKey(id));
    }



}
