package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiListResult;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
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

    List<TradePlatformApi> findAllBySign(String sign);

    List<TradePlatformApi> findAllByUserId(int userId);

    List<TradePlatformApiListResult> findAllByUser(int userId);

    TradePlatformApi selectByTradePlatformId(int tradePlatformId);

    TradePlatformApi selectByUserId(int userId);

    int checkExistByTradePlatformIdAndUserId (int tradePlatformId, int userId);

    int checkExistByKeyAndStatus (int userId,String sign,int status);

    int checkExistByIdAndUserId(int id,int userId);

    int checkExistByUserIdAndNickName(int userId,String nickName);

    int updateIsBound(@Param("id") int id, @Param("isBound") int isBound, @Param("updatedAt") Timestamp updatedAt);


}