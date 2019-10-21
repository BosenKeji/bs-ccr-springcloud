package cn.bosenkeji;

import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.IUserProductComboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApp.class)
public class UserProductComboTest {

    @Resource
    private UserProductComboMapper userProductComboMapper;

    @Resource
    private IUserProductComboService iUserProductComboService;


    @Test
    public void testSelectByGroup() {
    }


}
