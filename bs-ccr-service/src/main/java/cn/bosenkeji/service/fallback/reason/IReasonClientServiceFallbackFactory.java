package cn.bosenkeji.service.fallback.reason;

import cn.bosenkeji.service.reason.IReasonClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.reason.Reason;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class IReasonClientServiceFallbackFactory implements FallbackFactory<IReasonClientService> {

    @Override
    public IReasonClientService create(Throwable cause) {
        return new IReasonClientService() {
            @Override
            public Map getSomeReason(Set<Integer> ids) {

                Reason reason=new Reason();
                reason.setId(0);
                reason.setName("hystrix");
                Map<Integer,Reason> map=new HashMap<>();
                map.put(reason.getId(),reason);
                return map;
            }

            @Override
            public Reason getOneReason(int id) {
                Reason reason=new Reason();
                reason.setId(0);
                reason.setName("hystrix");
                return reason;
            }

            @Override
            public PageInfo list(int pageNum, int pageSize) {
                List list=new ArrayList();
                Reason reason=new Reason();
                reason.setId(0);
                reason.setName("hystrix");
                list.add(reason);
                return new PageInfo(list);

            }

            @Override
            public PageInfo listByReasonType(int id, int pageNum, int pageSize) {
                List list=new ArrayList();
                Reason reason=new Reason();
                reason.setId(0);
                reason.setName("hystrix");
                list.add(reason);
                return new PageInfo(list);
            }

            @Override
            public Result<Integer> checkExistById(int id) {
                return new Result<>(-1,"hystrix");
            }
        };
    }
}
