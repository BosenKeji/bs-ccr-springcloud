package cn.bosenkeji.service;

import cn.bosenkeji.vo.reason.Reason;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReasonService {

    int add(Reason reason);
    int updateBySelective(Reason reason);
    int delete(int id);

    Reason get(int id);
    List<Reason> list();

    PageInfo<Reason> selectByReasonType(int reasonTypeId,int pageNum,int pageSize);
    PageInfo<Reason> listWithPage(int pageNum,int pageSize);

    Map<Integer, Reason> selectByPrimaryKeys(Set<Integer> ids);

    Integer checkExistById(int id);
}
