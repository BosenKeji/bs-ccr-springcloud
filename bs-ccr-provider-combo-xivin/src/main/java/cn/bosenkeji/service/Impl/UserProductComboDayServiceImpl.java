package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.service.IAdminClientService;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboDayService;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IUserClientService iUserClientService;

    @Resource
    private IAdminClientService iAdminClientService;

    @Override
    public int add(UserProductComboDay userProductComboDay) {
        //添加缓存
        int id = userProductComboDay.getUserProductComboId();
        String key="userproductcombo:id_"+id;
        Long expire = redisTemplate.getExpire(key, TimeUnit.DAYS);

        if(expire>0) {
            //设置有效时间
            redisTemplate.expire(key,expire+userProductComboDay.getNumber(),TimeUnit.DAYS);
            return userProductComboDayMapper.insert(userProductComboDay);
        }
        //当原来的用户套餐已过时时返回false
        return 0;
    }

    @Override
    public int update(UserProductComboDay userProductComboDay) {
        return userProductComboDayMapper.updateByPrimaryKeySelective(userProductComboDay);
    }

    @Override
    public PageInfo<UserProductComboDay> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(userProductComboDayMapper.findAll());
    }

    @Override
    public UserProductComboDay get(int id) {
        return userProductComboDayMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<UserProductComboDay> selectByUserTel(String tel, int pageNum, int pageSize) {
        User user=null;
        try {
            user = iUserClientService.getOneUserByTel(tel);
        }catch (Exception e) {
            System.out.println("获取用户发生异常"+e.getMessage());
        }
        if(user!=null) {
            PageHelper.startPage(pageNum,pageSize);
            List<UserProductComboDay> userProductComboDays = userProductComboDayMapper.selectByUserId(user.getId());
            for (UserProductComboDay userProductComboDay : userProductComboDays) {
                userProductComboDay.setUser(user);
                try {

                    Admin admin = iAdminClientService.get(userProductComboDay.getUserProductComboDayByAdmin().getAdminId()).get();
                    if(admin!=null) {
                        userProductComboDay.getUserProductComboDayByAdmin().setAdmin(admin);
                    }
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return new PageInfo<>(userProductComboDays);
        }
        return new PageInfo<>();
    }

    @Override
    public PageInfo<UserProductComboDay> selectByUserProductComboId(int userProductComboId, int pageNum, int pageSize) {
        //User user = iUserClientService.getOneUser(tel);

        PageHelper.startPage(pageNum,pageSize);
        List<UserProductComboDay> userProductComboDays = userProductComboDayMapper.selectByUserProductComboId(userProductComboId);
        for (UserProductComboDay userProductComboDay : userProductComboDays) {
            try {
                User user = iUserClientService.getOneUser(userProductComboDay.getUserId());
                if(user!=null) {
                    userProductComboDay.setUser(user);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            try {

                Admin admin = iAdminClientService.get(userProductComboDay.getUserProductComboDayByAdmin().getAdminId()).get();
                if(admin!=null) {

                    userProductComboDay.getUserProductComboDayByAdmin().setAdmin(admin);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return new PageInfo<>(userProductComboDays);
    }
}
