package cn.bosenkeji.utils;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.JobService;
import cn.bosenkeji.vo.combo.UserProductCombo;
import org.springframework.data.redis.core.RedisTemplate;

public class UserComboTimeUtil {


    /**
     * 计算 用户套餐的剩余时间 保存在哪个redis 以及 是否需要创建任务调度
     * 将一个用户套餐时长保存到 redis里面
     * userComboTime_i  用来保存时长，每个键只保存5000 个用户套餐
     * comboRedisKey 用户记录 用户套餐ID 的时长保存在 哪个键 即userComboTime_i 中的i时多少
     *
     * @param time 用户套餐的时长
     * @param redisTemplate redisTemplate
     * @param userProductCombo 用户套餐信息
     * @param jobService 任务调度服务
     */
    public static void saveComboTimeToRedis(Integer time,RedisTemplate redisTemplate,UserProductCombo userProductCombo,JobService jobService) {

        String idStr = String.valueOf(userProductCombo.getId());
        String currentKey="";
        int key=0;
        while (true) {
            currentKey = UserComboRedisEnum.UserComboTime+"_"+key;
            Long size = redisTemplate.opsForZSet().size(currentKey);

            //如果不存在则创建key 并创建任务调度
            //创建任务调度
            if (!redisTemplate.hasKey(currentKey)) {
                System.out.println("创建任务调度——————————————————调度");
                try {
                     // 把调度任务的执行时间分开，此处有待优化
                    if(key*5<60) {
                        String timeExpression = "0 " + key * 5 + " 0 * * ?";
                        jobService.createJob(currentKey, timeExpression, currentKey);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(size==0) {

                // 保存
                redisTemplate.opsForZSet().add(UserComboRedisEnum.UserComboTime+"_"+key,idStr,time+1);
                break;

            }
            //0到5000 直接保存时长
            else if(size<UserComboRedisEnum.maxSize){
                redisTemplate.opsForZSet().add(UserComboRedisEnum.UserComboTime+"_"+key,idStr,time+1);
                break;
            }
            // 已存在则判断 zset 的长度，超过5000个则 创建新的zset 即循环一次
            else {
                key ++;
            }


        }

        //把 当前用户套餐的id 保存到hash 里面
        redisTemplate.opsForHash().put(UserComboRedisEnum.ComboRedisKey,idStr,currentKey);

    }


}
