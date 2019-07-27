package cn.bosenkeji.service;

import cn.bosenkeji.vo.tradeplateform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-26 15:46
 */
public interface TradePlatformApiBindProductComboService {

    PageInfo<TradePlatformApiBindProductCombo> findByUserIdWithPage(int userId,int pageNum,int pageSize);

    Optional<Integer> add(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo);

}
