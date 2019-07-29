package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;

import java.util.List;

public interface TradePlatformApiBindProductComboMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradePlatformApiBindProductCombo record);

    int insertSelective(TradePlatformApiBindProductCombo record);

    TradePlatformApiBindProductCombo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradePlatformApiBindProductCombo record);
    int updateApiByPrimaryKey(TradePlatformApiBindProductCombo record);

    int updateByPrimaryKey(TradePlatformApiBindProductCombo record);

    List<TradePlatformApiBindProductCombo> findByUserId(Integer userId);

    List<TradePlatformApiMapper> findNotInBindAndInApiListByUserId(Integer userId);

    List<Integer> findNotInBindAndInComboIdsByUserId(Integer userId);
}