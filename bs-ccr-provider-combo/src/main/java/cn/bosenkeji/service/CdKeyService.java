package cn.bosenkeji.service;

import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.CdKey;
import cn.bosenkeji.vo.cdKey.CdKeyOther;
import com.github.pagehelper.PageInfo;

public interface CdKeyService {

    Result generateCdKeys(Integer num ,Integer productComboId, String prefix, String remark);

    PageInfo<CdKeyOther> getCdKeyByPage(Integer pageNum, Integer pageSize);

    Result activate(Integer userId, String username, String key);

    Result renew(Integer userId, String username, Integer userProductComboId, String key);
}
