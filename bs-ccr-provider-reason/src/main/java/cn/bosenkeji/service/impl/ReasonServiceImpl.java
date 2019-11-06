package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.ReasonMapper;
import cn.bosenkeji.service.ReasonService;
import cn.bosenkeji.vo.reason.Reason;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ReasonServiceImpl implements ReasonService {

    @Resource
    private ReasonMapper reasonMapper;

    @Override
    public int add(Reason reason) {
        return reasonMapper.insertSelective(reason);
    }

    @Override
    public int updateBySelective(Reason reason) {
        return reasonMapper.updateByPrimaryKeySelective(reason);
    }

    @Override
    public int delete(int id) {
        return reasonMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Reason get(int id) {
        return reasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Reason> list() {
        return reasonMapper.findAll();
    }

    @Override
    public PageInfo<Reason> selectByReasonType(int reasonTypeId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(reasonMapper.selectByReasonType(reasonTypeId));
    }

    @Override
    public PageInfo<Reason> listWithPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(list());
    }

    @Override
    public Map<Integer, Reason> selectByPrimaryKeys(Set<Integer> ids) {
        return this.reasonMapper.selectByPrimaryKeys(ids);
    }

    @Override
    public Integer checkExistById(int id) {
        return this.reasonMapper.checkExistById(id);
    }
}
