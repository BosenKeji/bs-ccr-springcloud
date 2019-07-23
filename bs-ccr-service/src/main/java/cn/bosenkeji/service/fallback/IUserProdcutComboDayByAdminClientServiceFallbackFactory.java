package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IProductComboClientService;
import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.service.IUserProductComboDayByAdminClientService;
import cn.bosenkeji.vo.ProductCombo;
import cn.bosenkeji.vo.UserProductComboDay;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
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
public class IUserProdcutComboDayByAdminClientServiceFallbackFactory implements FallbackFactory<IUserProductComboDayByAdminClientService> {
    @Override
    public IUserProductComboDayByAdminClientService create(Throwable throwable) {
        return new IUserProductComboDayByAdminClientService() {
            @Override
            public Optional<Integer> add(UserProductComboDay userProductComboDay,int adminId) {
                return Optional.empty();
            }

            @Override
            public List listByUserProductComboId(int userProductComboId) {
                List list=new ArrayList();
                UserProductComboDayByAdmin userProductComboDayByAdmin=new UserProductComboDayByAdmin();
                list.add(userProductComboDayByAdmin);
                return list;
            }

            @Override
            public PageInfo list(int pageNum, int pageSize) {
                List list=new ArrayList();
                UserProductComboDayByAdmin userProductComboDayByAdmin=new UserProductComboDayByAdmin();
                list.add(userProductComboDayByAdmin);
                return new PageInfo<>(list);
            }


            @Override
            public PageInfo listByUserTel(int pageNum, int pageSize, String userTel) {
                List list=new ArrayList();
                UserProductComboDayByAdmin userProductComboDayByAdmin=new UserProductComboDayByAdmin();
                list.add(userProductComboDayByAdmin);
                return new PageInfo<>(list);
            }
        };
    }
}
