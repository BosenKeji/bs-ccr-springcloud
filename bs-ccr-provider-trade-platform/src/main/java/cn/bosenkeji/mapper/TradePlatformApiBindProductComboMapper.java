package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;

import java.util.List;
import java.util.Set;

public interface TradePlatformApiBindProductComboMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradePlatformApiBindProductCombo record);

    int insertSelective(TradePlatformApiBindProductCombo record);

    TradePlatformApiBindProductCombo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradePlatformApiBindProductCombo record);
    int updateApiByPrimaryKey(TradePlatformApiBindProductCombo record);

    int updateByPrimaryKey(TradePlatformApiBindProductCombo record);

    List<TradePlatformApiBindProductCombo> findByUserId(Integer userId);

    //List<TradePlatformApiMapper> findNotInBindAndInApiListByUserId(Integer userId);

    //List<Integer> findNotInBindAndInComboIdsByUserId(Integer userId);

    int checkExistNotBindApiByUserIdAndTradePlatformApiId(Integer userId,Integer tradePlatformApiId);
    //int checkExistNotBindComboByUserIdAndUserProductComboId(Integer userId,Integer userProductComboId);
    int checkExistByUserIdAndId(Integer userId,Integer id);

    List<Integer> selectApiIdsByUserId(Integer userId);
    List<Integer> selectComboIdsByUserId(Integer userId);

    int updateApiIdByPrimaryKey(Integer id,Integer tradePlatformApiId);

    TradePlatformApiBindProductCombo getByUserIdAndComboId(Integer userId,Integer userProductComboId);

    int deleteByComboId(Integer userProductComboId);

    int checkExistByComboId(Integer userProductComboId);

    List<TradePlatformApiBindProductCombo> findAll();

    int getUserIdById(int id);

    TradePlatformApiBindProductCombo selectByComboId(Integer comboId);
    List<TradePlatformApiBindProductCombo> selectByComboIds(Set<Integer> collection);

}