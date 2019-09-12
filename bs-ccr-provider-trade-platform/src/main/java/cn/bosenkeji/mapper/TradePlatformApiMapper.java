package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/15 14:51
 */

public interface TradePlatformApiMapper {
    int deleteByTradePlatformKey(int id);

    int deleteByPrimaryKey(int id);

    int insert(TradePlatformApi record);

    int insertSelective(TradePlatformApi record);

    TradePlatformApi selectByPrimaryKey(int id);


    int updateByPrimaryKeySelective(TradePlatformApi record);

    int updateByPrimaryKey(TradePlatformApi record);

    List<TradePlatformApi> findAll();

    List<TradePlatformApi> findAllByUserId(int userId);

    TradePlatformApi selectByTradePlatformId(int tradePlatformId);

    TradePlatformApi selectByUserId(int userId);

    int checkExistByTradePlatformIdAndUserId (int tradePlatformId, int userId);

    int checkExistByKeyAndStatus (String accessKey, String secretKey,int status);

    int checkExistByIdAndUserId(int id,int userId);

    int checkExistByUserIdAndNickName(int userId,String nickName);

    int checkExistByKey(String accessKey,String secretKey);

}