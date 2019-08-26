package cn.bosenkeji.handler;

import cn.bosenkeji.messaging.MySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName DealTest
 * @Description TODO
 * @Author hjh
 * @Date 2019-08-26 18:12
 * @Version 1.0
 */

@Component
public class DealTest {

    @Autowired
    private MySource source;

    @StreamListener("input1")
    public void input1(String msg) {
        System.out.println("input1"+msg);
    }

    @StreamListener("input1")
    public void input2(String msg) {
        System.out.println("input2"+msg);
    }
}
