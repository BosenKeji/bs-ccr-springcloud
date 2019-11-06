package cn.bosenkeji;

import cn.bosenkeji.service.ComboDayByAdminReasonService;
import cn.bosenkeji.vo.combo.ComboDayByAdminReason;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import scala.Int;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApp.class)
public class ComboDayByAdminReasonTest {

    @Resource
    private ComboDayByAdminReasonService comboDayByAdminReasonService;

    @Test
    public void testSelectByPrimarykeys() {

        List<Integer> list=new ArrayList();
        list.add(54);
        list.add(55);
        Map<Integer,ComboDayByAdminReason> map = comboDayByAdminReasonService.selectByPrimaryKeys(list);
        for (ComboDayByAdminReason value : map.values()) {
            System.out.println("value = " + value);
        }
        System.out.println("map = " + map);
    }
}
