package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.ComboDayByAdminReasonMapper;
import cn.bosenkeji.service.ComboDayByAdminReasonService;
import cn.bosenkeji.service.reason.IReasonClientService;
import cn.bosenkeji.vo.combo.ComboDayByAdminReason;
import cn.bosenkeji.vo.reason.Reason;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ComboDayByAdminReasonServiceImpl implements ComboDayByAdminReasonService {

    @Resource
    private ComboDayByAdminReasonMapper comboDayByAdminReasonMapper;

    @Resource
    private IReasonClientService iReasonClientService;

    @Override
    public int add(ComboDayByAdminReason comboDayByAdminReason) {
        return comboDayByAdminReasonMapper.insertSelective(comboDayByAdminReason);
    }

    @Override
    public ComboDayByAdminReason get(int id) {
        return comboDayByAdminReasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<ComboDayByAdminReason> listWithPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        return new PageInfo<>(comboDayByAdminReasonMapper.findAll());
    }

    @Override
    public Map<Integer, ComboDayByAdminReason> selectByPrimaryKeys(List<Integer> ids) {
        Map<Integer, ComboDayByAdminReason> reasonMap = this.comboDayByAdminReasonMapper.listByComboDayByAdminIds(ids);
        fillReasonByIds(reasonMap);
        return reasonMap;
    }

    public Map<Integer, ComboDayByAdminReason> fillReasonByIds(Map<Integer, ComboDayByAdminReason> map) {

        if(null != map && map.size()>0) {
            Set<Integer> ids = new HashSet<>();
            for (ComboDayByAdminReason comboDayByAdminReason : map.values()) {
                if(comboDayByAdminReason.getReasonId()>0)
                    ids.add(comboDayByAdminReason.getReasonId());
            }

            if(ids.size()>0) {
                Map<Integer,Reason> someReason = iReasonClientService.getSomeReason(ids);
                if(null != someReason) {
                    for (ComboDayByAdminReason comboDayByAdminReason : map.values()) {
                        int reasonId = comboDayByAdminReason.getReasonId();
                        if (reasonId > 0 && someReason.containsKey(reasonId)) {
                            Reason reason = someReason.get(reasonId);
                            comboDayByAdminReason.setReason(reason);
                            map.put(comboDayByAdminReason.getId(), comboDayByAdminReason);
                        }
                    }
                }
            }

        }
        return map;
    }

}
