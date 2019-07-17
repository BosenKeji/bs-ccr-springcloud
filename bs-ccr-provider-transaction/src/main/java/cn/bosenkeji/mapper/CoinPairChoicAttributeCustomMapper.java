package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.CoinPairChoicAttributeCustom;

/**
 * @Author CAJR
 * @create 2019/7/17 10:12
 */

public interface CoinPairChoicAttributeCustomMapper {
    int deleteByPrimaryKey(int id);

    int insert(CoinPairChoicAttributeCustom record);

    int insertSelective(CoinPairChoicAttributeCustom record);

    CoinPairChoicAttributeCustom selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(CoinPairChoicAttributeCustom record);

    int updateByPrimaryKey(CoinPairChoicAttributeCustom record);
}