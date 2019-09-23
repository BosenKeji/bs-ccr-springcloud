package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IUserProductComboDayClientService;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-18 11:30
 */
@Component
public class IUserProductComboDayClientServiceFallbackFactory implements FallbackFactory<IUserProductComboDayClientService> {
    @Override
    public IUserProductComboDayClientService create(Throwable throwable) {
        return new IUserProductComboDayClientService() {

            @Override
            public PageInfo listByTel(String tel, int pageNum, int pageSize) {
                UserProductComboDay userProductComboDay=new UserProductComboDay();
                List list=new ArrayList();
                list.add(userProductComboDay);
                return new PageInfo(list);
            }

            @Override
            public PageInfo listByUserProductComboId(int userProductComboId, int pageNum, int pageSize) {
                UserProductComboDay userProductComboDay=new UserProductComboDay();
                List list=new ArrayList();
                list.add(userProductComboDay);
                return new PageInfo(list);
            }

            @Override
            public PageInfo list(int pageNum, int pageSize) {
                UserProductComboDay userProductComboDay=new UserProductComboDay();
                List list=new ArrayList();
                list.add(userProductComboDay);
                return new PageInfo(list);
            }
        };
    }
}
