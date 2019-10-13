package cn.bosenkeji.utils;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.JobService;
import cn.bosenkeji.vo.combo.UserProductCombo;
import org.springframework.data.redis.core.RedisTemplate;

public class UserComboTimeUtil {

    /**
     * 保存时长方法
     * @param currentKey
     * @param time
     * @param key
     * @param jobService
     * @param redisTemplate
     * @param userProductCombo
     * @param userProductComboMapper
     */
    public static void addTime(String currentKey,int time, int key, JobService jobService, RedisTemplate redisTemplate, UserProductCombo userProductCombo, UserProductComboMapper userProductComboMapper) {

        String idStr=String.valueOf(userProductCombo.getId());
        while (true) {
            currentKey = UserComboRedisEnum.UserComboTime + "_" + key;
            Long size = redisTemplate.opsForZSet().size(currentKey);
            if(size==0) {
                //如果键不存在则 需要创建调度任务
                if(!redisTemplate.hasKey(currentKey)) {
                    try {
                        if(key*5<60) {
                            String timeExpression = "0 " + key * 5 + " 0 * * ?";
                            jobService.createJob(currentKey, timeExpression, currentKey);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //保存时长
                redisTemplate.opsForZSet().add(currentKey,idStr,time+1);
                break;
            }
            else if(size<=UserComboRedisEnum.maxSize) {

                redisTemplate.opsForZSet().add(currentKey,idStr,time+1);
                break;

            }
            else {
                key++;
            }
        }
        //更新redisKey
        userProductCombo.setRedisKey(currentKey);
        userProductComboMapper.updateRedisKey(userProductCombo);
    }
}
