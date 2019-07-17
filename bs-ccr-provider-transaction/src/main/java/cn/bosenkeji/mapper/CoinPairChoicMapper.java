package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.CoinPairChoic;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/17 10:09
 */

public interface CoinPairChoicMapper {
    int deleteByPrimaryKey(int id);

    int insert(CoinPairChoic record);

    int insertSelective(CoinPairChoic record);

    CoinPairChoic selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairChoic record);

    int updateByPrimaryKey(CoinPairChoic record);

    List<CoinPairChoic> findAll();
}