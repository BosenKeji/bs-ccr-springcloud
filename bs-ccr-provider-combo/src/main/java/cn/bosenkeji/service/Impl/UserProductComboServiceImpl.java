package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.ProductComboMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.vo.UserProductCombo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
//@CacheConfig(cacheNames = "userproductcombo")
public class UserProductComboServiceImpl implements IUserProductComboService {

    @Resource
    private UserProductComboMapper userProductComboMapper;

    @Resource
    private ProductComboMapper productComboMapper;

    @Resource
    private RedisTemplate redisTemplate;

   // @Cacheable(key = "'userProductCombo'+#p0.id")
    @Override
    public boolean add(UserProductCombo userProductCombo) {
        //查询套餐时长
        int time=productComboMapper.selectTimeByPrimaryKey(userProductCombo.getProductComboId());

        //保存到数据库
        userProductComboMapper.insert(userProductCombo);

        //添加缓存
        redisTemplate.opsForValue().set("userproductcombo:id_"+userProductCombo.getId(),userProductCombo,time,TimeUnit.DAYS);
        return true;

    }

    @Override
    public boolean update(UserProductCombo userProductCombo) {
        return userProductComboMapper.updateByPrimaryKeySelective(userProductCombo);
    }

    @Override
    public PageInfo<UserProductCombo> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<UserProductCombo>(userProductComboMapper.findAll());
    }

    @Override
    public Optional<UserProductCombo> get(int id) {

        try {
            //从缓存中读取
            String key="userproductcombo:id_"+id;
            UserProductCombo userProductCombo = (UserProductCombo) redisTemplate.opsForValue().get(key);
            long time = redisTemplate.getExpire(key, TimeUnit.DAYS);
            userProductCombo.setRemainTime((int) time);
            if(userProductCombo==null||("").equals(userProductCombo))
                return Optional.ofNullable(userProductComboMapper.selectByPrimaryKey(id));
            return Optional.ofNullable(userProductCombo);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.ofNullable(userProductComboMapper.selectByPrimaryKey(id));
            //return null;
        }

    }

    /**
     * 单表查询，不满足需求
     * @param userId
     * @return
     */
    @Override
    public List<UserProductCombo> getByUserId(int userId) {

        //查询用户id列表
        List<Integer> ids=userProductComboMapper.selectPrimaryKeyByUserId(userId);

        //循环获取键值、有效时间 （同样区分是否在缓存中）
        List<UserProductCombo> list=new ArrayList<>();
        for (Integer id : ids) {

            //先查询redis
            String key="userproductcombo:id_"+id;
            UserProductCombo userProductCombo = (UserProductCombo) redisTemplate.opsForValue().get(key);
            if(userProductCombo==null||("").equals(userProductCombo)) {
                //没有时从数据库拿
                userProductCombo=userProductComboMapper.selectByPrimaryKey(id);
            }
            else {
                //如果是从缓存取出的则取出有效时间，否则有效时间为0
                long time = redisTemplate.getExpire(key, TimeUnit.DAYS);
                userProductCombo.setRemainTime((int) time);

            }
            list.add(userProductCombo);
        }


        return list;

    }


    @Override
    public PageInfo<UserProductCombo> selectUserProductComboByUserId(int pageNum,int pageSize,int userId) {

        //从数据库查询
        PageHelper.startPage(pageNum,pageSize);
        List<UserProductCombo> userProductCombos = userProductComboMapper.selectProductCombo();

        for (UserProductCombo userProductCombo : userProductCombos) {
            //设置有效时间
            int time = userProductCombo.getProductCombo().getTime();
            int id = userProductCombo.getId();
            String key="userproductcombo:id_"+id;
            userProductCombo.setRemainTime(redisTemplate.getExpire(key,TimeUnit.DAYS).intValue());
        }
        return new PageInfo<>(userProductCombos);
    }


}
