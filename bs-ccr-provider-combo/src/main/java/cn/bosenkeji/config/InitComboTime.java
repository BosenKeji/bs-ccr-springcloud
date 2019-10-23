package cn.bosenkeji.config;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.IUserProductComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InitComboTime implements ApplicationRunner {

    @Autowired
    private IUserProductComboService iUserProductComboService;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(!redisTemplate.hasKey(UserComboRedisEnum.UserComboTime)) {
            System.out.println("准备初始化数据");
            System.out.println(iUserProductComboService.flushAllComboDay());
        }


    }
}
