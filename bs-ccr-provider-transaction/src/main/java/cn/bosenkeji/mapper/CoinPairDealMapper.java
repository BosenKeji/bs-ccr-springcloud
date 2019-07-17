package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.CoinPairDeal;

/**
 * @Author CAJR
 * @create 2019/7/17 10:13
 */

public interface CoinPairDealMapper {
    int deleteByPrimaryKey(int id);

    int insert(CoinPairDeal record);

    int insertSelective(CoinPairDeal record);

    CoinPairDeal selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairDeal record);

    int updateByPrimaryKey(CoinPairDeal record);
}