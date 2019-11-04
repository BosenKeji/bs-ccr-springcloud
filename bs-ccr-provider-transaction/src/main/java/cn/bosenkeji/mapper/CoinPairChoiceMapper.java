package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.transaction.CoinPairChoice;
import cn.bosenkeji.vo.transaction.CoinPairChoiceJoinCoinPair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/17 10:09
 */

public interface CoinPairChoiceMapper {
    int deleteByPrimaryKey(int id);

    int batchDelete(List<Integer> id);

    int insert(CoinPairChoice record);

    int insertSelective(CoinPairChoice record);

    CoinPairChoice selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairChoice record);

    int updateByPrimaryKey(CoinPairChoice record);

    Integer checkExistByCoinPartnerIdAndRobotId(int coinPartnerId,int userId);

    List<CoinPairChoice> findAllByTradePlatformApiBindProductComboId (int tradePlatformApiBindProductComboId);

    List<CoinPairChoice> findAll();

    List<Integer> findAllCoinPairChoiceId();
}