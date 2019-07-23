package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboDayByAdminMapper;
import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.mapper.UserProductComboRedisTemplate;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.vo.UserProductCombo;
import cn.bosenkeji.vo.UserProductComboDay;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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


    @Override
    public Optional<Integer> add(UserProductComboDay userProductComboDay,int adminId) {

        //新增用户套餐时长
        userProductComboDayMapper.insert(userProductComboDay);
        //添加缓存
        int id = userProductComboDay.getUserProductComboId();

        Long expire = userProductComboRedisTemplate.getExpire(id);

        /*if(expire>0) {
            //设置有效时间
            userProductComboRedisTemplate.setExpire(id,expire+userProductComboDay.getNumber());
            //return userProductComboDayMapper.insert(userProductComboDay);
        }*/

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

    /**
     * 多表联合查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<UserProductComboDayByAdmin> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo(userProductComboDayByAdminMapper.selectUserProductComboDayByAdminList());
    }

    @Override
    public Optional<UserProductComboDayByAdmin> get(int id) {
        return Optional.ofNullable(userProductComboDayByAdminMapper.selectByPrimaryKey(id));
    }

    @Override
    public List<UserProductComboDayByAdmin> getByUserProductComboId(int userProductComboId) {
        return this.userProductComboDayByAdminMapper.selectUserProductComboDayByUserProductComboId(userProductComboId);
    }

    @Override
    public PageInfo<UserProductComboDayByAdmin> getByUserTel(String userTel,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(this.userProductComboDayByAdminMapper.selectUserProductComboDayByUserTel(userTel));
    }
}
