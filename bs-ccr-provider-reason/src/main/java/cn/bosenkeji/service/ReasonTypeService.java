package cn.bosenkeji.service;

import cn.bosenkeji.vo.reason.ReasonType;
import com.github.pagehelper.PageInfo;

public interface ReasonTypeService {

    int add(ReasonType reasonType);
    int updateBySelective(ReasonType reasonType);
    int delete(int id);

    ReasonType get(int id);
    PageInfo<ReasonType> listWithPage(int pageNum,int pageSize);
}
