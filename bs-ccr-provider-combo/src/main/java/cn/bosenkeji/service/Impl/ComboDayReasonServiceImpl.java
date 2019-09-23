/**
 * FileName: ComboDayReasonServiceImpl
 * Author: xivin
 * Date: 2019-09-23 10:28
 * Description:
 */
package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.ComboDayReasonMapper;
import cn.bosenkeji.service.ComboDayReasonService;
import cn.bosenkeji.vo.combo.ComboDayReason;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ComboDayReasonServiceImpl implements ComboDayReasonService {

    @Resource
    private ComboDayReasonMapper comboDayReasonMapper;

    @Override
    public List<ComboDayReason> list() {
        return this.comboDayReasonMapper.findAll();
    }

    @Override
    public PageInfo<ComboDayReason> getByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(this.list());
    }

    @Override
    public ComboDayReason selectByPrimaryKey(int id) {
        return this.comboDayReasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertBySelective(ComboDayReason comboDayReason) {
        return this.comboDayReasonMapper.insertSelective(comboDayReason);
    }

    @Override
    public int updateBySelective(ComboDayReason comboDayReason) {
        return this.comboDayReasonMapper.updateByPrimaryKeySelective(comboDayReason);
    }

    @Override
    public int delete(int id) {
        return this.comboDayReasonMapper.deleteByPrimaryKey(id);
    }
}
