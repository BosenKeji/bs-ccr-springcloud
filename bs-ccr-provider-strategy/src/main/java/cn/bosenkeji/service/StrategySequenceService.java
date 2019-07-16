package cn.bosenkeji.service;


import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import com.github.pagehelper.PageInfo;

public interface StrategySequenceService {

    PageInfo<StrategySequence> findAll(Integer pageNum, Integer pageSize);

    StrategySequenceVO findSequenceByPrimaryKey(Integer id);

    String getSequenceValueByStrategyId(Integer strategyId);
}
