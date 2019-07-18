package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.CoinPairChoicAttribute;

/**
 * @Author CAJR
 * @create 2019/7/17 10:10
 */

public interface CoinPairChoicAttributeMapper {
    int deleteByPrimaryKey(int id);

    int insert(CoinPairChoicAttribute record);

    int insertSelective(CoinPairChoicAttribute record);

    CoinPairChoicAttribute selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairChoicAttribute record);

    int updateByPrimaryKey(CoinPairChoicAttribute record);

    CoinPairChoicAttribute selectBycoinPartnerChoicId(int coinPartnerChoicId);
}