package cn.bosenkeji.job;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.interfaces.ExpiredComboRedisKey;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class UserComboTask extends JavaProcessor {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultRedisScript<Boolean> redisScript;


    private final int ZERO = 0;
    private final int DEFAULT_MAX_VALUE = 10000;
    private final int ONE = 1;
    private final int TWO = 2;

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

        String jobParameters = context.getJobParameters();
        if(StringUtils.isNotBlank(jobParameters)) {

            List list=new ArrayList();
            //获取剩余0天的 用户套餐
            //把这些套餐的时长从 缓存中清除
            Set eq0Set = redisTemplate.opsForZSet().rangeByScore(jobParameters, ZERO, ZERO);
            Iterator eq0Iterator = eq0Set.iterator();
            while (eq0Iterator.hasNext()) {
                String next = String.valueOf(eq0Iterator.next());
                list.add(next);
            }
            if(list.size()>0) {

                redisTemplate.opsForZSet().remove(jobParameters, eq0Set.toArray());
                redisTemplate.opsForHash().delete(UserComboRedisEnum.ComboRedisKey, list.toArray());

                //把已经过期的userProductComboId 放到redis
                redisTemplate.opsForSet().add(ExpiredComboRedisKey.expiredUserProductComboIdSet,eq0Set.toArray());
            }


        }
        return super.postProcess(context);
    }

    /**
     * 任务调度执行主方法类
     * @param jobContext
     * @return
     * @throws Exception
     */
    @Override
    public ProcessResult process(JobContext jobContext) throws Exception {

        //默认最大值为1000
        long maxScore=DEFAULT_MAX_VALUE;
        JobContext jobContext1=jobContext;

        //获取要操作的键
        String jobParameters = jobContext.getJobParameters();

        if(StringUtils.isBlank(jobParameters)) {
            Log.error(jobContext.getJobId()+"调度任务 没有参数，请检查是否成功设置参数!!!");
            return new ProcessResult(false);
        }

        //从 redis查询最大值
        Set<ZSetOperations.TypedTuple> set = redisTemplate.opsForZSet().reverseRangeWithScores(jobParameters, ZERO, ZERO);
        Iterator<ZSetOperations.TypedTuple> iterator = set.iterator();
        if(iterator.hasNext()) {
            Double score = iterator.next().getScore();
            if (score!=null&&score>TWO) {
                maxScore=score.longValue()+1;
            }
        }
        //获取大于2的
        Set  gt2Set= redisTemplate.opsForZSet().rangeByScore(jobParameters, TWO, maxScore);
        List<String> list=new ArrayList<>();

        //获取剩余1天的
        Set eq1Set = redisTemplate.opsForZSet().rangeByScore(jobParameters, ONE, ONE);

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
