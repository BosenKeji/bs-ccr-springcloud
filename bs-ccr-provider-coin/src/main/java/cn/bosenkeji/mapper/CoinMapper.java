package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.coin.Coin;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/9 16:27
 */

public interface CoinMapper {
    int deleteByPrimaryKey(Integer id);

    Integer insert(Coin record);

    int insertSelective(Coin record);

    Coin selectByPrimaryKey(Integer id);

    Coin selectByField(String field);

    int updateByPrimaryKeySelective(Coin record);

    int updateByPrimaryKey(Coin record);

    List<Coin> findAll();
}