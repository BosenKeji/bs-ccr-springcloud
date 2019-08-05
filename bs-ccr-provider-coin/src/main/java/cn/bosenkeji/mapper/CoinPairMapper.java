package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.coin.CoinPair;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/9 16:27
 */

public interface CoinPairMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CoinPair record);

    int insertSelective(CoinPair record);

    CoinPair selectByPrimaryKey(Integer id);

    CoinPair selectByName(String name);

    Integer checkExistByName(String name);

    int updateByPrimaryKeySelective(CoinPair record);

    int updateByPrimaryKey(CoinPair record);

    List<CoinPair> findAll();
}