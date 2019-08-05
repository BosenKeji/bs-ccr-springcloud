package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.ProductComboMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.mapper.UserProductComboRedisTemplate;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.UserProductCombo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private UserProductComboRedisTemplate userProductComboRedisTemplate;

    @Resource
    private IUserClientService iUserClientService;

   // @Cacheable(key = "'userProductCombo'+#p0.id")
    @Override
    public Optional<Integer> add(UserProductCombo userProductCombo) {

        //判断用户是否已买过该产品  如果该用户已买过该产品，则不能部署机器人
        /*if(userProductComboMapper.selectCountByProductId(userProductCombo.getProductCombo().getProductId(),userProductCombo.getUserId())>0)
            return Optional.ofNullable(0);*/

        //查询套餐时长
        int time=productComboMapper.selectTimeByPrimaryKey(userProductCombo.getProductComboId());

        //保存到数据库
        userProductComboMapper.insert(userProductCombo);

        //添加缓存
        userProductComboRedisTemplate.add(userProductCombo,time);
        return Optional.ofNullable(1);

    }

    @Override
    public Optional<Integer> update(UserProductCombo userProductCombo) {
        return Optional.ofNullable(userProductComboMapper.updateByPrimaryKeySelective(userProductCombo));
    }

    @Override
    public PageInfo<UserProductCombo> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<UserProductCombo>(userProductComboMapper.findAll());
    }

    //多表查询
    @Override
    public Optional<UserProductCombo> get(int id) {

        //数据库联表查询用户套餐信息
        UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(id);

        //根据id获取用户套餐的有效时长
        final long time = userProductComboRedisTemplate.getExpire(id);
        if(time>0) {
            userProductCombo.setRemainTime((int)time);
        }else {
            userProductCombo.setRemainTime(0);
        }

        return Optional.ofNullable(userProductCombo);

        //以下单表查询，目前用处不大
        /*try {

            //从缓存中读取
            UserProductCombo userProductCombo = userProductComboRedisTemplate.get(id);
            final long time = userProductComboRedisTemplate.getExpire(id);



            userProductCombo.setRemainTime((int) time);
            if(userProductCombo==null||("").equals(userProductCombo))
                return Optional.ofNullable(userProductComboMapper.selectByPrimaryKey(id));
            return Optional.ofNullable(userProductCombo);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.ofNullable(userProductComboMapper.selectByPrimaryKey(id));
            //return null;
        }*/

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

            UserProductCombo userProductCombo = userProductComboRedisTemplate.get(id);

            if(userProductCombo==null||("").equals(userProductCombo)) {
                //没有时从数据库拿
                userProductCombo=userProductComboMapper.selectByPrimaryKey(id);
            }
            else {
                //如果是从缓存取出的则取出有效时间，否则有效时间为0
                long time = userProductComboRedisTemplate.getExpire(id);

                userProductCombo.setRemainTime((int) time);

            }
            list.add(userProductCombo);
        }


        return list;

    }


    /**
     * 联合查询用户套餐时长列表
     * @param pageNum
     * @param pageSize
     * @param userTel
     * @return
     */
    @Override
    public PageInfo<UserProductCombo> selectUserProductComboByUserTel(int pageNum,int pageSize,String userTel) {

        PageHelper.startPage(pageNum,pageSize);
        User user = iUserClientService.getOneUserByTel(userTel);

        //查不到用户
        if(user==null) {
            return new PageInfo<>();
        }
        //从数据库查询

        List<UserProductCombo> userProductCombos = userProductComboMapper.selectUserProductComboByUserId(user.getId());

        for (UserProductCombo userProductCombo : userProductCombos) {

            //从缓存拿去剩余时间
            int id = userProductCombo.getId();
            long time=userProductComboRedisTemplate.getExpire(id);

            userProductCombo.setRemainTime((int)time);
        }
        return new PageInfo<>(userProductCombos);
    }

    /**
     * 联合查询用户套餐时长列表
     * @param pageNum
     * @param pageSize
     * @param userId 用户ID
     * @return
     */
    @Override
    public PageInfo<UserProductCombo> selectUserProductComboByUserId(int pageNum,int pageSize,int userId) {

        //从数据库查询
        PageHelper.startPage(pageNum,pageSize);
        List<UserProductCombo> userProductCombos = userProductComboMapper.selectUserProductComboByUserId(userId);

        for (UserProductCombo userProductCombo : userProductCombos) {

            //从缓存拿去剩余时间
            int id = userProductCombo.getId();
            long time=userProductComboRedisTemplate.getExpire(id);

            userProductCombo.setRemainTime((int)time);
        }
        return new PageInfo<>(userProductCombos);
    }

    @Override
    public Optional<Integer> checkExistByProductIdAndUserId(int productComboId,int userId) {
        int productId=productComboMapper.selectProductIdByPrimaryKey(productComboId);
        return Optional.ofNullable(userProductComboMapper.checkExistByProductIdAndUserId(productId,userId));
    }
}
