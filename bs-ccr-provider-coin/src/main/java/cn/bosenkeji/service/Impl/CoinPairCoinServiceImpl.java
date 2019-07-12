package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairCoinMapper;
import cn.bosenkeji.service.CoinPairCoinService;
import cn.bosenkeji.vo.CoinPairCoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:11
 */
@Service
public class CoinPairCoinServiceImpl implements CoinPairCoinService {

    @Autowired
    CoinPairCoinMapper coinPairCoinMapper;

    @Override
    public List<CoinPairCoin> list() {
        return coinPairCoinMapper.findAll();
    }

    @Override
    public Optional<CoinPairCoin> get(int id) {
        return Optional.ofNullable(coinPairCoinMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean add(CoinPairCoin coinPairCoin) {
        return coinPairCoinMapper.insertSelective(coinPairCoin) == 1;
    }

    @Override
    public boolean update(CoinPairCoin coinPairCoin) {
        return coinPairCoinMapper.updateByPrimaryKeySelective(coinPairCoin) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinPairCoinMapper.deleteByPrimaryKey(id) == 1;
    }
}
