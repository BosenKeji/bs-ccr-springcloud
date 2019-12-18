package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.coin.CoinSort;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/9 16:27
 */

public interface CoinSortMapper {
    int deleteByPrimaryKey(Integer id);

    Integer deleteByTradePlatformIdAndCoinId(int tradePlatformId,int coinId);

    int insert(CoinSort record);

    int insertSelective(CoinSort record);

    CoinSort selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CoinSort record);

    int updateByPrimaryKey(CoinSort record);

    List<CoinSort> findAll();

    List<CoinSort> findAllByTradePlatformIdAndType(Integer tradePlatformId,int type);

    Integer checkByTradePlatformIdAndCoinId(Integer tradePlatformId,Integer coinId);
}