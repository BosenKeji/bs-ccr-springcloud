package cn.bosenkeji;

import cn.bosenkeji.exception.enums.CoinEnum;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.Coin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author CAJR
 * @create 2019/7/9 17:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testCoin {

    @Autowired
    CoinService coinService;

    @Test
    public void addCoin(){
        System.out.println(CoinEnum.NAME.getCode());
    }
}
