package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.ComboDayByAdminReasonMapper;
import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.ComboDayByAdminReasonService;
import cn.bosenkeji.service.IAdminClientService;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboDayService;
import cn.bosenkeji.service.reason.IReasonClientService;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.ComboDayByAdminReason;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import cn.bosenkeji.vo.reason.Reason;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
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
    private UserProductComboMapper userProductComboMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ComboDayByAdminReasonService comboDayByAdminReasonService;

    @Resource
    private IUserClientService iUserClientService;

    @Resource
    private IAdminClientService iAdminClientService;



    @Override
    public PageInfo<UserProductComboDay> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserProductComboDay> all = userProductComboDayMapper.findAll();

        List<Integer> userIds=new ArrayList<>();
        List<Integer> adminIds=new ArrayList<>();

        for (UserProductComboDay userProductComboDay : all) {
            userIds.add(userProductComboDay.getUserId());
            if(null != userProductComboDay.getUserProductComboDayByAdmin())
                adminIds.add(userProductComboDay.getUserProductComboDayByAdmin().getAdminId());
        }

        getUserByIds(all,userIds);
        getAdminByIds(all,adminIds);

        return new PageInfo<>(all);
    }

    @Override
    public UserProductComboDay get(int id) {
        return userProductComboDayMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<UserProductComboDay> selectByUserTel(String tel, int pageNum, int pageSize) {
        User user=null;
        user = iUserClientService.getOneUserByTel(tel);
        return this.listByUserId(user,pageNum,pageSize);
    }


    public PageInfo<UserProductComboDay> listByUserId(User user, int pageNum, int pageSize) {
        if(user!=null) {

            List<Integer> adminIds=new ArrayList<>();
            List<Integer> byAdminId=new ArrayList<>();

            PageHelper.startPage(pageNum,pageSize);
            List<UserProductComboDay> userProductComboDays = userProductComboDayMapper.selectByUserId(user.getId());
            for (UserProductComboDay userProductComboDay : userProductComboDays) {
                userProductComboDay.setUser(user);
                int adminId=userProductComboDay.getUserProductComboDayByAdmin().getAdminId();
                Integer upcdba_id = userProductComboDay.getUserProductComboDayByAdmin().getId();
                if(adminId>0) {
                    adminIds.add(adminId);
                }
                if(upcdba_id != null && upcdba_id >0) {
                    byAdminId.add(upcdba_id);
                }
            }
            if(adminIds.size()>0) {
                this.getAdminByIds(userProductComboDays,adminIds);
            }
            if(byAdminId.size()>0) {
                this.getComboDayByAdminReasons(userProductComboDays,byAdminId);
            }
            return new PageInfo<>(userProductComboDays);
        }
        return new PageInfo<>();

    }

    @Cacheable(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,key = "#userProductComboId+'-'+#pageNum+'-'+#pageSize")
    @Override
    public PageInfo<UserProductComboDay> selectByUserProductComboId(int userProductComboId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<UserProductComboDay> userProductComboDays = userProductComboDayMapper.selectByUserProductComboId(userProductComboId);


        UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(userProductComboId);
        if(userProductCombo==null)
            return new PageInfo<>();
        Integer userId = userProductCombo.getUserId();
        User user=null;
        if(userId!=null&&userId>0) {
            user = iUserClientService.getOneUser(userId);
        }

        List<Integer> adminIds=new ArrayList<>();
        List<Integer> byAdminId=new ArrayList<>();

        for (UserProductComboDay userProductComboDay : userProductComboDays) {

            if(user!=null) {
                userProductComboDay.setUser(user);
            }
            if(userProductComboDay.getUserProductComboDayByAdmin()!=null) {
                Integer adminId = userProductComboDay.getUserProductComboDayByAdmin().getAdminId();
                Integer upcdba_id = userProductComboDay.getUserProductComboDayByAdmin().getId();
                if (adminId != null && userId > 0) {
                    adminIds.add(adminId);
                }
                if(upcdba_id != null && upcdba_id >0) {
                    byAdminId.add(upcdba_id);
                }
            }


        }

        if(adminIds.size()>0) {
            Map<Integer,Admin> adminMap=iAdminClientService.listByIds(adminIds);
            for (UserProductComboDay upcd : userProductComboDays) {
                if(upcd.getUserProductComboDayByAdmin()!=null) {
                    int aId = upcd.getUserProductComboDayByAdmin().getAdminId();
                    if (aId > 0 && adminMap.containsKey(aId)) {
                        upcd.getUserProductComboDayByAdmin().setAdmin(adminMap.get(aId));
                    }
                }
            }
        }

        getComboDayByAdminReasons(userProductComboDays,byAdminId);
        return new PageInfo<>(userProductComboDays);
    }

    public List<UserProductComboDay> getAdminByIds(List<UserProductComboDay> userProductComboDays,List<Integer> adminIds) {

        if(adminIds.size()>0) {
            Map<Integer,Admin> adminMap=iAdminClientService.listByIds(adminIds);
            for (UserProductComboDay upcd : userProductComboDays) {
                if(upcd.getUserProductComboDayByAdmin()!=null) {
                    int aId = upcd.getUserProductComboDayByAdmin().getAdminId();
                    if (aId > 0 && adminMap.containsKey(aId)) {
                        upcd.getUserProductComboDayByAdmin().setAdmin(adminMap.get(aId));
                    }
                }
            }
        }
        return userProductComboDays;
    }

    public List<UserProductComboDay> getUserByIds(List<UserProductComboDay> userProductComboDays,List<Integer> userIds) {
        if(userIds.size()>0) {
            Map<Integer,User> userMap=iUserClientService.listByIds(userIds);
            for (UserProductComboDay upcd : userProductComboDays) {
                int uId=upcd.getUserId();
                if(uId>0&&userMap.containsKey(uId)) {
                    upcd.setUser(userMap.get(uId));
                }
            }
        }
        return userProductComboDays;
    }

    public List<UserProductComboDay> getComboDayByAdminReasons(List<UserProductComboDay> userProductComboDays,List<Integer> ids) {
        if (ids.size()>0) {

            Map<Integer, ComboDayByAdminReason> reasonMap = comboDayByAdminReasonService.selectByPrimaryKeys(ids);

            //填充关联的套餐时长事由
            for(UserProductComboDay comboDay:userProductComboDays) {
                if(comboDay.getUserProductComboDayByAdmin()!=null) {
                    UserProductComboDayByAdmin comboDayByAdmin = comboDay.getUserProductComboDayByAdmin();
                    int cid = comboDayByAdmin.getId();
                    for (ComboDayByAdminReason value : reasonMap.values()) {

                        if(cid == value.getUserProductComboDayByAdminId()) {
                            comboDayByAdmin.setComboDayByAdminReason(value);
                        }
                    }

                }
            }

        }

        return userProductComboDays;
    }



}
