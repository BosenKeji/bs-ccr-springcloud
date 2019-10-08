package cn.bosenkeji.job;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.JobService;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class SimpleTask extends JavaProcessor {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultRedisScript<Boolean> redisScript;

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

        //获取要操作的键
        String jobParameters = jobContext.getJobParameters();

        //从 redis查询最大值
        Set<ZSetOperations.TypedTuple> set = redisTemplate.opsForZSet().reverseRangeWithScores(jobParameters, 0, 0);
        Iterator<ZSetOperations.TypedTuple> iterator = set.iterator();
        if(iterator.hasNext()) {
            Double score = iterator.next().getScore();
            if (score!=null&&score>2) {
                maxScore=score.longValue()+1;
            }
        }
        //获取大于2的
        Set  gt2Set= redisTemplate.opsForZSet().rangeByScore(jobParameters, 2, maxScore);
        List<String> list=new ArrayList<>();

        //获取剩余1天的
        Set eq1Set = redisTemplate.opsForZSet().rangeByScore(jobParameters, 1, 1);

        //处理剩余时长大于2的用户套餐
        Iterator gt2Iterator = gt2Set.iterator();
        while (gt2Iterator.hasNext()) {
            String next = String.valueOf(gt2Iterator.next());
            list.add(next);
        }

        Log.info("剩余时长大于2的套餐以完成-1 操作");
        //处理剩余时长为1的套餐，即即将到期的套餐
        Iterator eq1Iterator = eq1Set.iterator();
        while (eq1Iterator.hasNext()) {
            String next = String.valueOf(eq1Iterator.next());
            list.add(next);
        }


        Boolean execute = (Boolean) redisTemplate.execute(redisScript, list, "-1",jobParameters);
        assert execute!=null;
        Log.info(jobParameters+"时长剩余1的套餐以完成-1 操作");
        if(execute)
            System.out.println(execute);
        return new ProcessResult(execute);
    }
}
