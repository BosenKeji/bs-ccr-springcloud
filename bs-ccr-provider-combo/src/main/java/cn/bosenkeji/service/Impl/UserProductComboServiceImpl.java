package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.ProductComboMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.mapper.UserProductComboRedisTemplate;
import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.service.ITradePlatformApiBindProductComboClientService;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.ProductCombo;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.product.Product;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Resource
    private IProductClientService iProductClientService;

    @Resource
    private ITradePlatformApiBindProductComboClientService iTradePlatformApiBindProductComboClientService;

   // @Cacheable(key = "'userProductCombo'+#p0.id")
    @Override
    public int add(UserProductCombo userProductCombo) {

        //判断用户是否已买过该产品  如果该用户已买过该产品，则不能部署机器人
        /*if(userProductComboMapper.selectCountByProductId(userProductCombo.getProductCombo().getProductId(),userProductCombo.getUserId())>0)
            return Optional.ofNullable(0);*/

        //查询套餐时长
        int time=productComboMapper.selectTimeByPrimaryKey(userProductCombo.getProductComboId());

        //保存到数据库
        userProductComboMapper.insert(userProductCombo);

        //添加缓存
        userProductComboRedisTemplate.add(userProductCombo,time+1);

        TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo=new TradePlatformApiBindProductCombo();
        tradePlatformApiBindProductCombo.setUserProductComboId(userProductCombo.getId());
        tradePlatformApiBindProductCombo.setUserId(userProductCombo.getUserId());
        tradePlatformApiBindProductCombo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformApiBindProductCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        iTradePlatformApiBindProductComboClientService.addTradePlatformApiBindProductCombo(tradePlatformApiBindProductCombo);
        return 1;

    }

    @Override
    public int update(UserProductCombo userProductCombo) {
        return userProductComboMapper.updateByPrimaryKeySelective(userProductCombo);
    }

    @Override
    public PageInfo<UserProductCombo> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<UserProductCombo>(userProductComboMapper.findAll());
    }

    /**
     * 删除用户套餐功能，同时删除绑定记录，还有 redis的剩余时长
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {
        //UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(id);
        Result result = iTradePlatformApiBindProductComboClientService.deleteByComboId(id);
        int result1=(int) result.getData();
        int result2 = userProductComboMapper.deleteByPrimaryKey(id);
        userProductComboRedisTemplate.setExpire(id,0);
        System.out.println("result1:"+result1);
        System.out.println("result2:"+result2);
        if(result1==result2)
            return result1;
        else
            return result2;

    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        return 0;
    }

    //多表查询
    @Override
    public UserProductCombo get(int id) {

        //数据库联表查询用户套餐信息
        UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(id);

        //根据id获取用户套餐的有效时长
        final long time = userProductComboRedisTemplate.getExpire(id);
        if(time>0) {
            userProductCombo.setRemainTime((int)time);
        }else {
            userProductCombo.setRemainTime(0);
        }

        return userProductCombo;


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
        User user=null;

        try{
            user = iUserClientService.getOneUserByTel(userTel);
        }catch (Exception e) {
            e.printStackTrace();
            return new PageInfo<>();
        }


        //查不到用户
        if(user==null) {
            return new PageInfo<>();
        }
        //从数据库查询

        List<Integer> pids=new ArrayList<>();

        List<UserProductCombo> userProductCombos = userProductComboMapper.selectUserProductComboByUserId(user.getId());

        for (UserProductCombo userProductCombo : userProductCombos) {

            //从缓存拿去剩余时间
            int id = userProductCombo.getId();
            long time=userProductComboRedisTemplate.getExpire(id);

            userProductCombo.setRemainTime(time>0?(int)time:0);

            //循环收集产品ID
            pids.add(userProductCombo.getProductCombo().getProductId());
        }

        if(pids!=null&&pids.size()>0) {

            //通过多个id获取产品的 map
            Map<Integer, Product> productMap=iProductClientService.listByPrimaryKeys(pids);
            //循环把产品 映射到套餐中
            for (UserProductCombo userProductCombo:userProductCombos) {
                Integer id=userProductCombo.getProductCombo().getProductId();
                if(id!=null&&id>0&productMap.containsKey(id)) {
                    userProductCombo.getProductCombo().setProduct(productMap.get(id));
                }
            }
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
        List<Integer> pids=new ArrayList<>();

        for (UserProductCombo userProductCombo : userProductCombos) {

            //从缓存拿去剩余时间
            int id = userProductCombo.getId();
            long time=userProductComboRedisTemplate.getExpire(id);


            userProductCombo.setRemainTime(time>0?(int) time:0);

            //收集多个产品 ID
            pids.add(userProductCombo.getProductCombo().getProductId());
        }

        if(pids!=null&&pids.size()>0) {

            //通过多个产品ID查询产品列表
            Map<Integer, Product> productMap=iProductClientService.listByPrimaryKeys(pids);
            //逐一映射
            for (UserProductCombo userProductCombo:userProductCombos) {
                Integer id=userProductCombo.getProductCombo().getProductId();
                if(id!=null&&id>0&productMap.containsKey(id)) {
                    userProductCombo.getProductCombo().setProduct(productMap.get(id));
                }
            }
        }

        return new PageInfo<>(userProductCombos);
    }

    @Override
    public int checkExistByProductIdAndUserId(int productComboId,int userId) {
        int productId=productComboMapper.selectProductIdByPrimaryKey(productComboId);
        return userProductComboMapper.checkExistByProductIdAndUserId(productId,userId);
    }

    @Override
    public Map<Integer,UserProductCombo> selectByPrimaryKeys(List<Integer> ids) {
        Map<Integer,UserProductCombo> userProductCombosMap=userProductComboMapper.selectByPrimaryKeys(ids);
        List<UserProductCombo> userProductCombos=new ArrayList<>(userProductCombosMap.values());
        getProductByPids(userProductCombos);
        return userProductCombosMap;
    }

    public List<UserProductCombo> getProductByPids(List<UserProductCombo> userProductCombos) {
        List<Integer> pids=new ArrayList<>();
        for (UserProductCombo userProductCombo : userProductCombos) {

            //从缓存拿去剩余时间
            int id = userProductCombo.getId();
            long time=userProductComboRedisTemplate.getExpire(id);

            userProductCombo.setRemainTime(time>0?(int)time:0);

            //循环收集产品ID
            pids.add(userProductCombo.getProductCombo().getProductId());
        }

        if(pids!=null&&pids.size()>0) {

            //通过多个id获取产品的 map
            Map<Integer, Product> productMap=iProductClientService.listByPrimaryKeys(pids);
            //循环把产品 映射到套餐中
            for (UserProductCombo userProductCombo:userProductCombos) {
                Integer id=userProductCombo.getProductCombo().getProductId();
                if(id!=null&&id>0&productMap.containsKey(id)) {
                    userProductCombo.getProductCombo().setProduct(productMap.get(id));
                }
            }
        }
        return userProductCombos;
    }
}
