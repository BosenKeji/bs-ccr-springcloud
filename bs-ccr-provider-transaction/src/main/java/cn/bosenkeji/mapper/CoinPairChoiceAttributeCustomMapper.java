package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;

/**
 * @Author CAJR
 * @create 2019/7/17 10:12
 */

public interface CoinPairChoiceAttributeCustomMapper {
    int deleteByPrimaryKey(int id);

    int insert(CoinPairChoiceAttributeCustom record);

    int insertSelective(CoinPairChoiceAttributeCustom record);

    CoinPairChoiceAttributeCustom selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairChoiceAttributeCustom record);

    int updateByCoinPartnerChoiceId(CoinPairChoiceAttributeCustom record);

    int updateByPrimaryKey(CoinPairChoiceAttributeCustom record);

    CoinPairChoiceAttributeCustom selectByCoinPartnerChoiceId(int coinPartnerChoiceId);

    Integer checkByCoinPartnerChoiceId(int coinPartnerChoiceId);
}