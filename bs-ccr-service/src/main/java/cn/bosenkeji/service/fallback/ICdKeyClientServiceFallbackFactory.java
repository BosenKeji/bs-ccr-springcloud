package cn.bosenkeji.service.fallback;


import cn.bosenkeji.service.ICdKeyClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.*;
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
            public Result generateCdKeys(GenerateCdKeyParam generateCdKeyParam) {
                return new Result<>(0,"failed");
            }

            @Override
            public PageInfo<CdKeyOther> getCdKeyByPage(int pageNum, int pageSize) {
                PageInfo<CdKeyOther> pageInfo = new PageInfo<>();
                List<CdKeyOther> list = new ArrayList<>();
                CdKeyOther cdKeyOther = new CdKeyOther();
                cdKeyOther.setRemark("failed");
                pageInfo.setList(list);
                return pageInfo;
            }

            @Override
            public Result activate(ActivateCdKeyUserParam param) {
                return new Result<>(0,"failed");
            }

            @Override
            public Result renew(RenewCdKeyUserParam param) {
                return new Result<>(0,"failed");
            }

            @Override
            public PageInfo<CdKeyOther> getCdKeyBySearch(String cdKey, String username, Integer isUsed, Integer userProductComboId,  Integer sort, Integer pageNum, Integer pageSize) {
                PageInfo<CdKeyOther> pageInfo = new PageInfo<>();
                List<CdKeyOther> list = new ArrayList<>();
                CdKeyOther cdKeyOther = new CdKeyOther();
                cdKeyOther.setRemark("failed");
                pageInfo.setList(list);
                return pageInfo;
            }
        };
    }
}
