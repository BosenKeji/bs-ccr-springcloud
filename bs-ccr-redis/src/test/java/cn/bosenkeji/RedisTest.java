package cn.bosenkeji;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author xivin
 * @ClassName cn.bosenkeji
 * @Version V1.0
 * @create 2019-07-15 18:07
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = RedisApp.class)
public class RedisTest {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//
//
//
//    @Test
//    public void testPackage() {
//        for (int i=0;i<100;i++){
//            redisTemplate.opsForValue().set("test_springboot_redis_"+i+1111+"_"+i+2222+"_"+i,"测试springboot2 集成 redis"+i);
//        }
//        Set<String> str =  redisTemplate.keys("test_springboot_redis*");
//        List<String> stringList =new ArrayList<>(str);
//        Map<String,Object> stringObjectMap = new HashMap<>();
//        if (!stringList.isEmpty()) {
//            for (String s : stringList) {
//                stringObjectMap.put(s,redisTemplate.opsForValue().get(s));
//                System.err.println("key = " + s+"|value="+stringObjectMap.get(s));
//            }
//        }
//
//        System.out.println(stringObjectMap.size());
//    }
//
//    @Test
//    public void testGetValue() {
//        String str = String.valueOf(redisTemplate.opsForValue().get("test_springboot_redis"));
//        System.out.println("str = " + str);
//    }
//
//
//
//    @Test
//    public void testExpire() {
//        Long time = redisTemplate.getExpire("test_springboot_redis");
//        System.err.println("time = " + time);
//    }


}
