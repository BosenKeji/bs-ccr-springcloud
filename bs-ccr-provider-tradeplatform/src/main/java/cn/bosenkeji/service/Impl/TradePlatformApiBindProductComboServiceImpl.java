package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformApiBindProductComboMapper;
import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.service.TradePlatformApiBindProductComboService;
import cn.bosenkeji.vo.tradeplateform.TradePlatformApiBindProductCombo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.Impl
 * @Version V1.0
 * @create 2019-07-26 15:47
 */
@Service
public class TradePlatformApiBindProductComboServiceImpl implements TradePlatformApiBindProductComboService {

    @Autowired
    private TradePlatformApiBindProductComboMapper tradePlatformApiBindProductComboMapper;

    //注入用户套餐生产者
    @Resource
    private IUserProductComboClientService iUserProductComboClientService;

    @Override
    public List<TradePlatformApiBindProductCombo> findByUserId(int userId) {
        //查询用户下的
        List<TradePlatformApiBindProductCombo> list = tradePlatformApiBindProductComboMapper.findByUserId(userId);

        //分别查询对应的用户套餐——调用用户套餐的服务
        for (TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo : list) {
            tradePlatformApiBindProductCombo.setUserProductCombo(iUserProductComboClientService
                    .getUserProductCombo(tradePlatformApiBindProductCombo.getUserProductComboId()));
        }
        return list;
    }

    @Override
    public Optional<Integer> add(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
        return Optional.ofNullable(tradePlatformApiBindProductComboMapper.insertSelective(tradePlatformApiBindProductCombo));
    }
}
