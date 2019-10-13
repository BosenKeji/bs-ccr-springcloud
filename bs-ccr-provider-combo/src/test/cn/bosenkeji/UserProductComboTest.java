package cn.bosenkeji;

import cn.bosenkeji.ComboApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApp.class)
public class UserProductComboTest {

    @Test
    public void test() {
        System.out.println("this is test");
    }

}
