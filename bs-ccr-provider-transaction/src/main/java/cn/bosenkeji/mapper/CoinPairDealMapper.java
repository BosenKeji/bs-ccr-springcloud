package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.transaction.CoinPairDeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<CoinPairDeal> findCoinPairDealByUserId(Integer userId);

    List<CoinPairDeal> findCoinPairDealByUserIdAndChoicId(@Param("userId") Integer userId, @Param("choicId") Integer choicId);

    List<CoinPairDeal> findCoinPairDealByUserIdAndType(@Param("userId") Integer userId, @Param("type") Integer type);

    int countCoinPair(Integer userId);

    int countCoinPairDeal(@Param("userId") Integer userId, @Param("choicId") Integer choicId);

    int deleteBatchCoinPairDealByUserIdAndChoicId(@Param("userId") Integer userId, @Param("choicId") Integer choicId);

}