package bosenCCR;

import cn.bosenkeji.ComboApplication;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.vo.UserProductCombo;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xivin
 * @ClassName bosenCCR
 * @Version V1.0
 * @create 2019-07-16 19:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApplication.class)
public class UserProductComboTest {

    @Resource
    private UserProductComboMapper userProductComboMapper;

    @Test
    public void testSelectPrimaryKeyByUserId() {
        List ids=userProductComboMapper.selectPrimaryKeyByUserId(1);
        for (Object id : ids) {
            System.err.println(id.toString());
        }
    }
}
