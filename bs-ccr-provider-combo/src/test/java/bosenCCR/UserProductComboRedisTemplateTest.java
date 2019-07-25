package bosenCCR;

import cn.bosenkeji.ComboApp;
import cn.bosenkeji.mapper.UserProductComboRedisTemplate;
import cn.bosenkeji.vo.combo.UserProductCombo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author xivin
 * @ClassName bosenCCR
 * @Version V1.0
 * @create 2019-07-16 19:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApp.class)
public class UserProductComboRedisTemplateTest {

    @Resource
    private UserProductComboRedisTemplate userProductComboRedisTemplate;

    //测试引入的redis是否生效
    @Test
    public void testRedist() {

        UserProductCombo userProductCombo=new UserProductCombo();
        userProductCombo.setRemark("生效");
        userProductCombo.setStatus(1);
        userProductCombo.setId(99);
        long time=10;
        userProductComboRedisTemplate.add(userProductCombo,time);
        UserProductCombo userProductCombo1 = userProductComboRedisTemplate.get(userProductCombo.getId());
        System.out.println(userProductCombo1);
    }


}
