package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductCombo;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-18 11:30
 */
@Component
public class IUserProductComboClientServiceFallbackFactory implements FallbackFactory<IUserProductComboClientService> {
    @Override
    public IUserProductComboClientService create(Throwable throwable) {
        return new IUserProductComboClientService() {
            @Override
            public Result add(UserProductCombo userProductCombo) {
                return new Result(0,"hystrix");
            }

            @Override
            public PageInfo listByUserTel(String userTel, int pageNum, int pageSize) {
                List<UserProductCombo> list=new ArrayList<>();
                UserProductCombo userProductCombo=new UserProductCombo();
                userProductCombo.setRemark("hystrixName");
                list.add(userProductCombo);
                return new PageInfo<>(list);
            }

            @Override
            public UserProductCombo getUserProductCombo(int id) {
                UserProductCombo userProductCombo=new UserProductCombo();
                userProductCombo.setRemark("hystrixName");
                return userProductCombo;
            }

            @Override
            public PageInfo listByUserId(int userId, int pageNum, int pageSize) {

                UserProductCombo userProductCombo=new UserProductCombo();
                userProductCombo.setOrderNumber("hystrix");
                userProductCombo.setRemark("hystrix");
                List list=new ArrayList();
                list.add(userProductCombo);
                return new PageInfo(list);
            }

            @Override
            public Map<Integer, UserProductCombo> getByPrimaryKeys(List<Integer> ids) {
                Map<Integer,UserProductCombo> map=new HashMap<>();
                UserProductCombo userProductCombo=new UserProductCombo();
                userProductCombo.setId(0);
                userProductCombo.setOrderNumber("hystrix");
                userProductCombo.setRemark("hystrix");
                map.put(0,userProductCombo);
                return map;
            }
        };
    }
}
