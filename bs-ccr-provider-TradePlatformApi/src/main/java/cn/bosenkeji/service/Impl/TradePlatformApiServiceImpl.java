package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.vo.TradePlatformApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/15 14:59
 */

@Service
public class TradePlatformApiServiceImpl implements TradePlatformApiService {

    @Resource
    TradePlatformApiMapper tradePlatformApiMapper;

    @Override
    public List<TradePlatformApi> list() {
        return tradePlatformApiMapper.findAll();
    }

    @Override
    public Optional<TradePlatformApi> get(int id) {
        return Optional.ofNullable(tradePlatformApiMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean update(TradePlatformApi tradePlatformApi) {
        return tradePlatformApiMapper.updateByPrimaryKeySelective(tradePlatformApi) == 1;
    }

    @Override
    public boolean add(TradePlatformApi tradePlatformApi) {
        return tradePlatformApiMapper.insertSelective(tradePlatformApi) == 1;
    }

    @Override
    public boolean delete(int id) {
        return tradePlatformApiMapper.deleteByPrimaryKey(id) == 1 ;
    }
}
