package cn.bosenkeji.service.Impl;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.annotation.cache.BatchCacheRemove;
import cn.bosenkeji.interfaces.CommonStatusEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.ComboDayByAdminReasonMapper;
import cn.bosenkeji.mapper.UserProductComboDayByAdminMapper;
import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.service.JobService;
import cn.bosenkeji.service.reason.IReasonClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.UserComboTimeUtil;
import cn.bosenkeji.vo.combo.ComboDayByAdminReason;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.Impl
 * @Version V1.0
 * @create 2019-07-15 11:17
 * 增补时长操作，用来新加时长，而UserProductComboDayService 则针对时长管理，即查询都在UserProductComboDayService
 */
@Service
public class UserProductComboDayByAdminServiceImpl implements IUserProductComboDayByAdminService {

    @Resource
    private UserProductComboDayByAdminMapper userProductComboDayByAdminMapper;

    @Resource
    private UserProductComboDayMapper userProductComboDayMapper;

    @Resource
    private UserProductComboMapper userProductComboMapper;

    @Resource
    private ComboDayByAdminReasonMapper comboDayByAdminReasonMapper;

    @Resource
    private IReasonClientService iReasonClientService;

    @Resource
    private JobService jobService;

    @Resource
    private RedisTemplate redisTemplate;


    private final int SUCCESS=1;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());


    /**
     *  增补时长 注意用户套餐是否过期 而不同处理
     *  数据插入userProductComboDay 外，同时插入userProductComboDayByAdmin
     * @param userProductComboDay
     * @param userProductComboDayByAdmin
     * @return
     */
    @BatchCacheRemove(value = {"'ccr:comboDay:listByUpcId::'+#userProductComboDay.userProductComboId+'-'"},condition = "#result > 0")
    @Transactional
    @Override
    public int add(UserProductComboDay userProductComboDay, UserProductComboDayByAdmin userProductComboDayByAdmin) {

        try {
            //添加缓存
            int id = userProductComboDay.getUserProductComboId();
            UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(id);
            int time = userProductComboDay.getNumber();

            if (userProductCombo == null)
                return 0;
            //String redisKey = userProductCombo.getRedisKey();
            String redisKey = (String) redisTemplate.opsForHash().get(UserComboRedisEnum.ComboRedisKey, String.valueOf(id));


            // 减时长
            if (time < 0) {

                if (redisKey != null) {

                    Double currentScore = redisTemplate.opsForZSet().score(redisKey, String.valueOf(id));
                    System.out.println("current score = " + currentScore);
                    if (currentScore != null || currentScore != 0) {
                        if (currentScore + time < 0) {
                            redisTemplate.opsForZSet().add(redisKey, String.valueOf(id), 0);
                        } else {
                            redisTemplate.opsForZSet().incrementScore(redisKey, String.valueOf(id), time);
                        }
                    }
                }

            }

            // 增加时长
            else {
                //重新创建redis 的zset情况
                if (redisKey == null) {
                    UserComboTimeUtil.saveComboTimeToRedis(time, redisTemplate, userProductCombo, jobService);
                    //return 0;
                } else if (redisTemplate.opsForZSet().score(redisKey, String.valueOf(id)) == null) {

                    UserComboTimeUtil.saveComboTimeToRedis(time, redisTemplate, userProductCombo, jobService);

                }

                //redis中的时长还在，直接加长
                else {
                    redisTemplate.opsForZSet().incrementScore(redisKey, String.valueOf(id), time);
                }
            }

            if (userProductCombo.getUserId() > 0) {
                userProductComboDay.setUserId(userProductCombo.getUserId());
            }
            //新增用户套餐时长
            userProductComboDayMapper.insertSelective(userProductComboDay);

            //新增用户套餐时长操作
            userProductComboDayByAdmin.setUserProductComboDayId(userProductComboDay.getId());

            userProductComboDayByAdminMapper.insertSelective(userProductComboDayByAdmin);

            if (userProductComboDayByAdmin.getReasonId() > 0) {
                Result<Integer> result = iReasonClientService.checkExistById(userProductComboDayByAdmin.getReasonId());
                if (result.getData() > 0) {
                    ComboDayByAdminReason comboDayByAdminReason = new ComboDayByAdminReason();
                    comboDayByAdminReason.setUserProductComboDayByAdminId(userProductComboDayByAdmin.getId());
                    comboDayByAdminReason.setReasonId(userProductComboDayByAdmin.getReasonId());
                    comboDayByAdminReason.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    comboDayByAdminReason.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    comboDayByAdminReason.setStatus(CommonStatusEnum.NORMAL);
                    comboDayByAdminReasonMapper.insertSelective(comboDayByAdminReason);
                }
            }

            return SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }

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
