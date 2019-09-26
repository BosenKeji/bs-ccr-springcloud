package cn.bosenkeji.service.Impl;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.mapper.JobMapper;
import cn.bosenkeji.mapper.UserProductComboDayByAdminMapper;
import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.mapper.UserProductComboRedisTemplate;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.service.JobService;
import cn.bosenkeji.vo.Job;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JobMapper jobMapper;


    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private JobService jobService;



    private final Logger Log = LoggerFactory.getLogger(this.getClass());


    @Override
    public int add(UserProductComboDay userProductComboDay, UserProductComboDayByAdmin userProductComboDayByAdmin) {

        //添加缓存
        int id = userProductComboDay.getUserProductComboId();

        Double remainTime=redisTemplate.opsForZSet().score(UserComboRedisEnum.UserComboTime,String.valueOf(id));

        //如果剩余时长小于0，则要重新开始定时任务
        if(remainTime==null||remainTime<=0) {
            try {
                Job job = jobMapper.selectByJobName(String.valueOf(id));
                if(job!=null) {
                    jobService.enableJob(job.getJobId());
                    Log.info("定时任务"+id+"重新执行！！！");
                    //mySchedule.rescheduleJob(String.valueOf(id));
                }

            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        int time=userProductComboDay.getNumber()+1;
            redisTemplate.opsForZSet().incrementScore(UserComboRedisEnum.UserComboTime,String.valueOf(id),+time);

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
