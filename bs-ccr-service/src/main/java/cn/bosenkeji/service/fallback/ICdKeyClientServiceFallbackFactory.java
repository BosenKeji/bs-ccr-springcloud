package cn.bosenkeji.service.fallback;


import cn.bosenkeji.service.ICdKeyClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.CdKey;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ICdKeyClientServiceFallbackFactory implements FallbackFactory<ICdKeyClientService> {

    @Override
    public ICdKeyClientService create(Throwable throwable) {
        return new ICdKeyClientService() {
            @Override
            public Result getCdKeys(Integer num, Integer productComboId, String prefix, String remark) {
                return new Result<>(0,"failed");
            }

            @Override
            public PageInfo<CdKey> list(int pageNum, int pageSize) {
                PageInfo<CdKey> pageInfo = new PageInfo<>();
                List<CdKey> list = new ArrayList();
                CdKey cdKey = new CdKey();
                cdKey.setRemark("failed");
                pageInfo.setList(list);
                return pageInfo;
            }

            @Override
            public Result activate(Integer userId, String username, String key) {
                return new Result();
            }

            @Override
            public Result renew(Integer userId, String username, Integer userProductComboId, String key) {
                return null;
            }
        };
    }
}
