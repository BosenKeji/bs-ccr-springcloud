package cn.bosenkeji;

import cn.bosenkeji.mapper.ComboRedisKeyMapper;
import cn.bosenkeji.vo.combo.ComboRedisKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComboApp.class)
public class ComboRedisKeyTest {

    /*@Resource
    private ComboRedisKeyMapper comboRedisKeyMapper;

    @Test
    public void testAddComboRedis() {
        ComboRedisKey comboRedisKey=new ComboRedisKey();
        comboRedisKey.setName("userCombo_2");
        comboRedisKey.setStatus(1);
        System.out.println(comboRedisKeyMapper.insert(comboRedisKey));
    }

    @Test
    public void testGetComboRedis() {
        ComboRedisKey comboRedisKey = comboRedisKeyMapper.selectByPrimaryKey(1);
        System.out.println(comboRedisKey);
    }

    @Test
    public void testList() {
        List<ComboRedisKey> list = comboRedisKeyMapper.list();
        System.out.println("list = " + list);
    }

    @Test
    public void testUpdateSelective() {
        ComboRedisKey comboRedisKey=new ComboRedisKey();
        comboRedisKey.setName("userComboTime_0");
        comboRedisKey.setId(7);
        int i = comboRedisKeyMapper.updateByPrimaryKeySelective(comboRedisKey);
        System.out.println("i = " + i);
    }

    @Test
    public void testGetByName() {
        System.out.println(comboRedisKeyMapper.selectByName("userComboTime_0"));
    }*/

}
