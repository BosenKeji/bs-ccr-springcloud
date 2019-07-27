package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IUserProductComboDayByAdminClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
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
public class IUserProductComboDayByAdminClientServiceFallbackFactory implements FallbackFactory<IUserProductComboDayByAdminClientService> {
    @Override
    public IUserProductComboDayByAdminClientService create(Throwable throwable) {
        return new IUserProductComboDayByAdminClientService() {
            @Override
            public Result add(UserProductComboDay userProductComboDay,int adminId) {
                return new Result("hystrix","hystrix");
            }

            @Override
            public PageInfo listByUserProductComboId(int pageNum,int pageSize,int userProductComboId) {
                List list=new ArrayList();
                UserProductComboDayByAdmin userProductComboDayByAdmin=new UserProductComboDayByAdmin();
                list.add(userProductComboDayByAdmin);
                return new PageInfo(list);
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
