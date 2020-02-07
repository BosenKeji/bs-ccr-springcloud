package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/17 10:12
 */

public interface CoinPairChoiceAttributeCustomMapper {
    int deleteByPrimaryKey(int id);

    int deleteByCoinPairChoiceId(int coinPairChoiceId);

    int batchDelete(List<Integer> coinPairChoiceIds);

    int insert(CoinPairChoiceAttributeCustom record);

    int insertSelective(CoinPairChoiceAttributeCustom record);

    CoinPairChoiceAttributeCustom selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairChoiceAttributeCustom record);

    int updateByCoinPartnerChoiceId(CoinPairChoiceAttributeCustom record);

    int batchUpdateFirstOpenPrice(List<CoinPairChoiceAttributeCustom> coinPairChoiceAttributeCustoms);

    int updateByPrimaryKey(CoinPairChoiceAttributeCustom record);

    CoinPairChoiceAttributeCustom selectByCoinPartnerChoiceId(int coinPairChoiceId);

    Integer checkByCoinPartnerChoiceId(int coinPairChoiceId);
}