/**
 * FileName: ComboDayReason
 * Author: xivin
 * Date: 2019-09-23 10:25
 * Description:
 */
package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.ComboDayReason;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ComboDayReasonService {

    List<ComboDayReason> list();
    PageInfo<ComboDayReason> getByPage(int pageNum, int pageSize);
    ComboDayReason selectByPrimaryKey(int id);

    int insertBySelective(ComboDayReason comboDayReason);
    int updateBySelective(ComboDayReason comboDayReason);

    int delete(int id);

}
