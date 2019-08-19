package cn.bosenkeji.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName ExpensiveService
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Service
public class ExpensiveService {

//    @Cacheable("ccr-aws-cache")
    public String calculateExpensiveValue(String key){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return String.format("%s -> %s", key, UUID.randomUUID().toString());
    }
}
