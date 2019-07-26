package cn.bosenkeji.service;

import cn.bosenkeji.vo.tradeplateform.TradePlatformApiBindProductCombo;

import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-26 15:46
 */
public interface TradePlatformApiBindProductComboService {

    List<TradePlatformApiBindProductCombo> findByUserId(int userId);

    Optional<Integer> add(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo);

}
