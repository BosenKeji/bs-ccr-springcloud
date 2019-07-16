package bosenCCR;

import cn.bosenkeji.ComboApplication;
import cn.bosenkeji.vo.UserProductComboDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.concurrent.TimeUnit;

/**
 * @author xivin
 * @ClassName cn.bosenkeji
 * @Version V1.0
 * @create 2019-07-15 18:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSetAndGet() {
        redisTemplate.opsForValue().set("bs-ccr-redis-test","bs-ccr-redis-test-successful");
        String str=(String) redisTemplate.opsForValue().get("bs-ccr-redis-test");
        System.out.println("-----------------------------redis test------------------------------------");
        System.out.println(str);
        System.out.println("-----------------------------redis test------------------------------------");
    }

    @Test
    public void testAddProductDayCache() {
        UserProductComboDay userProductComboDay=new UserProductComboDay();
        userProductComboDay.setId(3);
        userProductComboDay.setNumber(100);
        redisTemplate.opsForValue().set("UserProductComboDay"+userProductComboDay.getId(),userProductComboDay,30,TimeUnit.DAYS);
        long time=redisTemplate.getExpire("UserProductComboDay"+userProductComboDay.getId(),TimeUnit.DAYS);
        UserProductComboDay product=(UserProductComboDay) redisTemplate.opsForValue().get("UserProductComboDay"+userProductComboDay.getId());
        System.out.println("-----------------------------redis test day------------------------------------");
        System.out.println(time+product.toString());
        System.out.println("-----------------------------redis test day------------------------------------");
    }

    @Test
    public void testPackage() {
        redisTemplate.opsForValue().set("a:b","測試分級款攢！！！");
        redisTemplate.opsForValue().set("a:c","1111測試分級款攢！！！");

    }

    @Test
    public void testPackageGet() {
        String str=(String) redisTemplate.opsForValue().get("comboid:userid:id");
        System.err.println(str);
    }
}
