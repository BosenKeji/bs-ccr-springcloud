package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinSortMapper;
import cn.bosenkeji.service.CoinSortService;
import cn.bosenkeji.vo.coin.CoinSort;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:21
 */
@Service
public class CoinSortServiceImpl implements CoinSortService {
    @Resource
    CoinSortMapper coinSortMapper;


    @Override
    public PageInfo listByTradePlatformId(int tradePlatformId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(this.coinSortMapper.findAllByTradePlatformId(tradePlatformId));
    }


    @Override
    public Optional<Integer> add(CoinSort coinSort) {
        return Optional.ofNullable(coinSortMapper.insertSelective(coinSort));
    }

    @Override
    public Optional<Integer> update(CoinSort coinSort) {
        return  Optional.ofNullable(coinSortMapper.updateByPrimaryKeySelective(coinSort) );
    }

    @Override
    public CoinSort get(int coinSortId) {
        return this.coinSortMapper.selectByPrimaryKey(coinSortId);
    }

    @Override
    public Optional<Integer> delete(int tradePlatform,int coinId) {
        return  Optional.ofNullable(coinSortMapper.deleteByTradePlatformIdAndCoinId(tradePlatform,coinId));
    }

    @Override
    public Optional<Integer> checkByTradePlatformIdAndCoinId(int tradePlatformId, int coinId) {
        return Optional.ofNullable(this.coinSortMapper.checkByTradePlatformIdAndCoinId(tradePlatformId, coinId));
    }
}
