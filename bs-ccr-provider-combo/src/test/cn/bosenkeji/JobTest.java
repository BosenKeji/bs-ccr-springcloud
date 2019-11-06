package cn.bosenkeji;

import cn.bosenkeji.mapper.JobMapper;
import cn.bosenkeji.vo.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApp.class)
public class JobTest {

    @Resource
    private JobMapper jobMapper;


    @Test
    public void testAddJob() {
        Job job=new Job();
        job.setJobName("userComboTime_0");
        jobMapper.insertSelective(job);
    }

    @Test
    public void testCheckExistByName() {
        int userComboTime_0 = jobMapper.checkExistByJobName("userComboTime_0");
        System.out.println("userComboTime_0 = " + userComboTime_0);
        int userComboTime_1 = jobMapper.checkExistByJobName("userComboTime_1");
        System.out.println("userComboTime_1 = " + userComboTime_1);
    }
}
