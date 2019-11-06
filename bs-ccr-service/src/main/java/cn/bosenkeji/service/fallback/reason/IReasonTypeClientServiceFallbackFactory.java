package cn.bosenkeji.service.fallback.reason;

import cn.bosenkeji.service.reason.IReasonTypeClientService;
import cn.bosenkeji.vo.reason.ReasonType;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IReasonTypeClientServiceFallbackFactory implements FallbackFactory<IReasonTypeClientService> {

    @Override
    public IReasonTypeClientService create(Throwable cause) {
        return new IReasonTypeClientService() {
            @Override
            public ReasonType getOneReasonType(int id) {
                ReasonType type=new ReasonType();
                type.setId(0);
                type.setName("hystrix");
                return type;
            }

            @Override
            public PageInfo list(int pageNum, int pageSize) {
                List list = new ArrayList();
                ReasonType type=new ReasonType();
                type.setId(0);
                type.setName("hystrix");
                list.add(type);
                return new PageInfo(list);
            }
        };
    }
}
