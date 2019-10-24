package cn.bosenkeji.config;


import cn.bosenkeji.service.IUserProductComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class InitComboTime implements ApplicationRunner {

    @Autowired
    private IUserProductComboService iUserProductComboService;


    @Override
    public void run(ApplicationArguments args) throws Exception {


        System.out.println("准备初始化数据");
        System.out.println(iUserProductComboService.initFlushAllComboDay());


    }
}
