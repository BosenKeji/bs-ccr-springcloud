package cn.bosenkeji.service;

import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.*;
import com.github.pagehelper.PageInfo;

public interface CdKeyService {

    Result generateCdKeys(GenerateCdKeyParam param);

    PageInfo<CdKeyOther> getCdKeyByPage(Integer pageNum, Integer pageSize);

    Result activate(ActivateCdKeyUserParam param);

    Result renew(RenewCdKeyUserParam param);

    PageInfo<CdKeyOther> getCdKeyBySearch(String cdKey, String username, Integer isUsed,Integer userProductComboId, Integer sort, Integer pageNum, Integer pageSize);
}
