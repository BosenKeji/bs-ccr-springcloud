package bosenCCR;

import cn.bosenkeji.ComboApplication;
import cn.bosenkeji.mapper.UserProductComboDayByAdminMapper;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xivin
 * @ClassName bosenCCR
 * @Version V1.0
 * @create 2019-07-17 19:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApplication.class)
public class UserProductComboDayByAdminTest {

    @Autowired
    private UserProductComboDayByAdminMapper userProductComboDayByAdminMapper;

    @Test
    public void testSelectUserProductComboDayByAdminByUPCID() {
        int userProductComboId=1;
        List<UserProductComboDayByAdmin> userProductComboDayByAdmins = userProductComboDayByAdminMapper.selectUserProductComboDayByUserProductComboId(userProductComboId);
        for (UserProductComboDayByAdmin userProductComboDayByAdmin : userProductComboDayByAdmins) {
            System.err.println(userProductComboDayByAdmin);
        }
    }

    @Test
    public void testTelectUserProductComboDayByUserTel() {
        List<UserProductComboDayByAdmin> userProductComboDayByAdmins = userProductComboDayByAdminMapper.selectUserProductComboDayByUserTel("13556559840");
        for (UserProductComboDayByAdmin userProductComboDayByAdmin : userProductComboDayByAdmins) {
            System.out.println("userProductComboDayByAdmin = " + userProductComboDayByAdmin);
        }
    }
}
