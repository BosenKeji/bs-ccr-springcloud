package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairCoinMapper;
import cn.bosenkeji.service.CoinPairCoinService;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:11
 */
@Service
public class CoinPairCoinServiceImpl implements CoinPairCoinService {

    @Resource
    CoinPairCoinMapper coinPairCoinMapper;

    @Override
    public List<CoinPairCoin> list() {
        return coinPairCoinMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(list());
    }

    @Override
    public Optional<CoinPairCoin> get(int id) {
        return Optional.ofNullable(coinPairCoinMapper.selectByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> add(CoinPairCoin coinPairCoin) {
        return Optional.ofNullable(coinPairCoinMapper.insertSelective(coinPairCoin));
    }

    @Override
    public Optional<Integer> update(CoinPairCoin coinPairCoin) {
        return Optional.ofNullable(coinPairCoinMapper.updateByPrimaryKeySelective(coinPairCoin));
    }

    @Override
    public Optional<Integer> delete(int coinId, int coinPairId) {
        return Optional.ofNullable(coinPairCoinMapper.deleteByCoinIdAndCoinPairId(coinId, coinPairId));
    }


}
