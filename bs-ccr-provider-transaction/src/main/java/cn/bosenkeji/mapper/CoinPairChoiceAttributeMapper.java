package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/17 10:10
 */

public interface CoinPairChoiceAttributeMapper {
    int deleteByPrimaryKey(int id);

    int insert(CoinPairChoiceAttribute record);

    int insertSelective(CoinPairChoiceAttribute record);

    CoinPairChoiceAttribute selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairChoiceAttribute record);

    int updateByCoinPartnerChoiceIdSelective(CoinPairChoiceAttribute record);


    int updateByPrimaryKey(CoinPairChoiceAttribute record);

    CoinPairChoiceAttribute selectByCoinPartnerChoiceId(int coinPartnerChoiceId);

    Integer checkByCoinPartnerChoiceId(int coinPartnerChoiceId);

    List<Integer> findAllCoinPartnerChoiceId();

    List<CoinPairChoiceAttribute> findSectionCoinPairChoiceAttributeByCoinPartnerChoiceIds(List<Integer> coinPartnerChoiceIds);
}