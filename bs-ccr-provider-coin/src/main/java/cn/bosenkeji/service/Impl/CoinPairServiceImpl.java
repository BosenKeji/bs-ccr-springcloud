package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairMapper;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.vo.CoinPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:12
 */
@Service
public class CoinPairServiceImpl implements CoinPairService {

    @Autowired
    CoinPairMapper coinPairMapper;

    @Override
    public List<CoinPair> list() {
        return coinPairMapper.findAll();
    }

    @Override
    public Optional<CoinPair> get(int id) {
        return Optional.ofNullable(coinPairMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean add(CoinPair coinPair) {
        return coinPairMapper.insertSelective(coinPair) == 1;
    }

    @Override
    public boolean update(CoinPair coinPair) {
        return coinPairMapper.updateByPrimaryKeySelective(coinPair) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinPairMapper.deleteByPrimaryKey(id) == 1;
    }
}
