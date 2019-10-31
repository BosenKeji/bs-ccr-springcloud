package cn.bosenkeji.service;

import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.CdKey;
import com.github.pagehelper.PageInfo;

public interface CdKeyService {

    Result getCdKeys(Integer num ,Integer productComboId, String prefix, String remark);

    PageInfo<CdKey> list(Integer pageNum, Integer pageSize);

    Result activate(Integer userId, String username, String key);

    Result renew(Integer userId, String username, Integer userProductComboId, String key);
}
