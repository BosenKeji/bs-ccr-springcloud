package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.vo.ProductCombo;
import cn.bosenkeji.vo.UserProductCombo;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-18 11:30
 */
@Component
public class IUserProdcutComboClientServiceFallbackFactory implements FallbackFactory<IUserProductComboClientService> {
    @Override
    public IUserProductComboClientService create(Throwable throwable) {
        return new IUserProductComboClientService() {
            @Override
            public boolean add(UserProductCombo userProductCombo) {
                return false;
            }

            @Override
            public PageInfo<UserProductCombo> listByUserTel(String userTel, int pageNum, int pageSize) {
                List<UserProductCombo> list=new ArrayList<>();
                UserProductCombo userProductCombo=new UserProductCombo();
                userProductCombo.setRemark("hystrixName");
                list.add(userProductCombo);
                return new PageInfo<>(list);
            }
        };
    }
}
