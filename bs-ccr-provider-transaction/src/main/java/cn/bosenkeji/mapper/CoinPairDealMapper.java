package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.CoinPairDeal;
import com.github.pagehelper.PageInfo;
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

    List<CoinPairDeal> findCoinPairDealByUserIdAndChoiceId(@Param("userId") Integer userId, @Param("choiceId") Integer choicId);

    List<CoinPairDeal> findCoinPairDealByUserIdAndType(@Param("userId") Integer userId, @Param("type") Integer type);

    int countCoinPair(Integer userId);

    int countCoinPairDeal(@Param("userId") Integer userId, @Param("choiceId") Integer choiceId);

    int deleteBatchCoinPairDealByUserIdAndChoiceId(@Param("userId") Integer userId, @Param("choiceId") Integer choicId);

}