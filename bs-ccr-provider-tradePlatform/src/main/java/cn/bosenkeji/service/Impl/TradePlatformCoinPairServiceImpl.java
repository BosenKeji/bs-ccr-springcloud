package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformCoinPairMapper;
import cn.bosenkeji.service.TradePlatformCoinPairService;
import cn.bosenkeji.vo.tradeplateform.TradePlatformCoinPair;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/16 14:56
 */
@Service
public class TradePlatformCoinPairServiceImpl implements TradePlatformCoinPairService {
    @Resource
    TradePlatformCoinPairMapper tradePlatformCoinPairMapper;

    @Override
    public List<TradePlatformCoinPair> list() {
        return tradePlatformCoinPairMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(list());
    }

    @Override
    public Optional<TradePlatformCoinPair> get(int id) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.selectByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> add(TradePlatformCoinPair tradePlatformCoinPair) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.insertSelective(tradePlatformCoinPair));
    }

    @Override
    public Optional<Integer> update(TradePlatformCoinPair tradePlatformCoinPair) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.updateByPrimaryKeySelective(tradePlatformCoinPair));
    }

    @Override
    public Optional<Integer> delete(int tradePlatformId, int coinPairId) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.deleteByTradePlatformIdAndCoinPairId(tradePlatformId, coinPairId));
    }


}
