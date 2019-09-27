package cn.bosenkeji.job;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.service.JobService;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;


import java.util.Iterator;
import java.util.Set;

@Component
public class SimpleTask extends JavaProcessor {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JobService jobService;

    @Override
    public void preProcess(JobContext context) {
        Log.info("执行前的操作");
        super.preProcess(context);
    }

    /**
     * 定时任务执行后的操作
     * @param context
     * @return
     */
    @Override
    public ProcessResult postProcess(JobContext context) {

        //Log.info("执行后的操作");
        return super.postProcess(context);
    }

    @Override
    public ProcessResult process(JobContext jobContext) throws Exception {

        //默认最大值为1000
        long maxScore=1000;
        //从 redis查询最大值
        Set<ZSetOperations.TypedTuple> set = redisTemplate.opsForZSet().reverseRangeWithScores(UserComboRedisEnum.UserComboTime, 0, 0);
        Iterator<ZSetOperations.TypedTuple> iterator = set.iterator();
        if(iterator.hasNext()) {
            Double score = iterator.next().getScore();
            if (score!=null&&score>2) {
                maxScore=score.longValue();
            }
        }
        //获取大于2的
        Set  gt2Set= redisTemplate.opsForZSet().rangeByScore(UserComboRedisEnum.UserComboTime, 2, maxScore);
        //获取剩余1天的
        Set eq1Set = redisTemplate.opsForZSet().rangeByScore(UserComboRedisEnum.UserComboTime, 1, 1);
        //处理剩余时长大于2的用户套餐
        Iterator gt2Iterator = gt2Set.iterator();
        while (gt2Iterator.hasNext()) {
            Object next = gt2Iterator.next();
            redisTemplate.opsForZSet().incrementScore(UserComboRedisEnum.UserComboTime,next,-1);
        }

        Log.info("剩余时长大于2的套餐以完成-1 操作");
        //处理剩余时长为1的套餐，即即将到期的套餐
        Iterator eq1Iterator = eq1Set.iterator();
        while (eq1Iterator.hasNext()) {
            Object next = eq1Iterator.next();
            redisTemplate.opsForZSet().incrementScore(UserComboRedisEnum.UserComboTime,next,-1);
        }
        Log.info("时长剩余1的套餐以完成-1 操作");


        return new ProcessResult(true);
    }
}
