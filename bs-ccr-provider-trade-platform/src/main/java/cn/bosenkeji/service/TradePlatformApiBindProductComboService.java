package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-26 15:46
 */
public interface TradePlatformApiBindProductComboService {

    PageInfo<TradePlatformApiBindProductCombo> findByUserIdWithPage(int userId,int pageNum,int pageSize);

    int add(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo);

    PageInfo<TradePlatformApi> findNoBindTradePlatformApiListByUserId(int userId,int pageNum,int pageSize);

    /**
     *
     * @param userId 用户id
     * @param pageNum 分页
     * @param pageSize 分页
     * @return
     */
    PageInfo<UserProductCombo> findNoBindUserProductComboListByUserId(int userId,int pageNum,int pageSize);

    int checkExistByUserIdAndTradePlatformApiId(int userId,int tradePlatformApiId);
    //int checkExistByUserIdAndUserProductComboId(int userId,int userProductComboId);
    int checkExistByUserIdAndId(int userId,int id);
    int updateBindApi(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo);
    int delete(int id);
    int removeBinding(int id);

    TradePlatformApiBindProductCombo getByUserIdAndComboId(int userId,int userProductComboId);

    int deleteByComboId(int userProductComboId);

    int checkExistByComboId(int userProductComboId);

    List findAll();

}
