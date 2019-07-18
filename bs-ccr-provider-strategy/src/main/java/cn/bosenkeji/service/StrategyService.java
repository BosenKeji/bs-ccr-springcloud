package cn.bosenkeji.service;


import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;


public interface StrategyService {

    StrategyVO getStrategy(Integer id);

    PageInfo<Strategy>  listByPage(Integer pageNum, Integer pageSize);


}
