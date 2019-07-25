package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.transaction.CoinPairChoice;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/17 10:09
 */

public interface CoinPairChoiceMapper {
    int deleteByPrimaryKey(int id);

    int insert(CoinPairChoice record);

    int insertSelective(CoinPairChoice record);

    CoinPairChoice selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairChoice record);

    int updateByPrimaryKey(CoinPairChoice record);

    Integer checkExistByCoinPartnerIdAndUserId(int coinPartnerId,int userId);

    List<CoinPairChoice> findAll(int userId,int coinId);
}