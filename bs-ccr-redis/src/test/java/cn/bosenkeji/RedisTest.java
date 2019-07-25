package cn.bosenkeji;


import cn.bosenkeji.vo.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xivin
 * @ClassName cn.bosenkeji
 * @Version V1.0
 * @create 2019-07-15 18:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApp.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;




    @Test
    public void testPackage() {
        redisTemplate.opsForValue().set("test:a","测试");
        String str = (String) redisTemplate.opsForValue().get("test:a");
        System.err.println("str = " + str);

    }



    @Test
    public void testExpire() {
        Long time = redisTemplate.getExpire("product2");
        System.err.println("time = " + time);
    }


}
