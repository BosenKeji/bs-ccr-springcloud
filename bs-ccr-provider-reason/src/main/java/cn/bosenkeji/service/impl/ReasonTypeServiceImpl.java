package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.ReasonTypeMapper;
import cn.bosenkeji.service.ReasonTypeService;
import cn.bosenkeji.vo.reason.ReasonType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReasonTypeServiceImpl implements ReasonTypeService {

    @Resource
    private ReasonTypeMapper reasonTypeMapper;

    @Override
    public int add(ReasonType reasonType) {
        return reasonTypeMapper.insertSelective(reasonType);
    }

    @Override
    public int updateBySelective(ReasonType reasonType) {
        return reasonTypeMapper.updateByPrimaryKeySelective(reasonType);
    }

    @Override
    public int delete(int id) {
        return reasonTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ReasonType get(int id) {
        return reasonTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<ReasonType> listWithPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(reasonTypeMapper.findAll());

    }
}
