/**
 * FileName: IComboDayReasonClientServiceFallbackFactory
 * Author: xivin
 * Date: 2019-09-23 10:56
 * Description:
 */
package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IComboDayReasonClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.ComboDayReason;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IComboDayReasonClientServiceFallbackFactory implements FallbackFactory<IComboDayReasonClientService> {

    @Override
    public IComboDayReasonClientService create(Throwable throwable) {
        return new IComboDayReasonClientService() {

            @Override
            public PageInfo listByPage(int pageNum, int pageSize) {
                ComboDayReason comboDayReason=new ComboDayReason();
                comboDayReason.setname("hystrix");
                comboDayReason.setId(-1);
                List<ComboDayReason> list=new ArrayList<>();
                list.add(comboDayReason);

                return new PageInfo(list);
            }

            @Override
            public ComboDayReason getOne(int id) {

                ComboDayReason comboDayReason=new ComboDayReason();
                comboDayReason.setname("hystrix");
                comboDayReason.setId(-1);
                return comboDayReason;
            }

            @Override
            public Result addOne(ComboDayReason comboDayReason) {
                return new Result(-1,"hystrix");
            }

            @Override
            public Result updateBySelective(ComboDayReason comboDayReason) {
                return new Result(-1,"hystrix");
            }

            @Override
            public Result deleteOne(int id) {
                return new Result(-1,"hystrix");
            }
        };
    }
}
