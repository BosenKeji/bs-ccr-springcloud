package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.ComboDayByAdminReason;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ComboDayByAdminReasonService {

    int add(ComboDayByAdminReason comboDayByAdminReason);
    ComboDayByAdminReason get(int id);
    PageInfo<ComboDayByAdminReason> listWithPage(int pageNum,int pageSize);

    Map<Integer,ComboDayByAdminReason> selectByPrimaryKeys(List<Integer> ids);

}
