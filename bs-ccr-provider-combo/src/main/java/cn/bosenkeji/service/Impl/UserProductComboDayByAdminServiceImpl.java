package cn.bosenkeji.service.Impl;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.job.MySchedule;
import cn.bosenkeji.mapper.UserProductComboDayByAdminMapper;
import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.mapper.UserProductComboRedisTemplate;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private RedisTemplate redisTemplate;


    @Resource
    private MySchedule mySchedule;

    @Resource
    private IUserClientService iUserClientService;


    private final Logger Log = LoggerFactory.getLogger(this.getClass());


    @Override
    public int add(UserProductComboDay userProductComboDay, UserProductComboDayByAdmin userProductComboDayByAdmin) {

        //添加缓存
        int id = userProductComboDay.getUserProductComboId();

        //JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(userProductComboDay.getUserProductComboId() + ""));
        //设置增加的有效时间
        //Long expire = userProductComboRedisTemplate.getExpire(id);
        Double remainTime=redisTemplate.opsForZSet().score(UserComboRedisEnum.UserComboTime,String.valueOf(id));

        //如果剩余时长小于0，则要重新开始定时任务
        if(remainTime==null||remainTime<=0) {
            try {

                mySchedule.rescheduleJob(String.valueOf(id));

                Log.info("定时任务"+id+"重新执行！！！");
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

            redisTemplate.opsForZSet().incrementScore(UserComboRedisEnum.UserComboTime,String.valueOf(id),+userProductComboDay.getNumber());

            //新增用户套餐时长
            userProductComboDayMapper.insert(userProductComboDay);
            //新增用户套餐时长操作
            userProductComboDayByAdmin.setUserProductComboDayId(userProductComboDay.getId());

            return userProductComboDayByAdminMapper.insert(userProductComboDayByAdmin);

    }

    @Override
    public int update(UserProductComboDayByAdmin userProductComboDayByAdmin) {
        return userProductComboDayByAdminMapper.updateByPrimaryKeySelective(userProductComboDayByAdmin);
    }



    @Override
    public UserProductComboDayByAdmin get(int id) {
        return userProductComboDayByAdminMapper.selectByPrimaryKey(id);
    }



}
