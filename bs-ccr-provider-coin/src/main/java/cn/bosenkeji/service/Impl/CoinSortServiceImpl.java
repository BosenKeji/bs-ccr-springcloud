package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinSortMapper;
import cn.bosenkeji.service.CoinSortService;
import cn.bosenkeji.vo.CoinSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:21
 */
@Service
public class CoinSortServiceImpl implements CoinSortService {
    @Autowired
    CoinSortMapper coinSortMapper;

    @Override
    public List<CoinSort> list() {
        return coinSortMapper.findAll();
    }

    @Override
    public Optional<CoinSort> get(int id) {
        return Optional.ofNullable(coinSortMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean add(CoinSort coinSort) {
        return coinSortMapper.insertSelective(coinSort) == 1;
    }

    @Override
    public boolean update(CoinSort coinSort) {
        return coinSortMapper.updateByPrimaryKeySelective(coinSort) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinSortMapper.deleteByPrimaryKey(id) == 1;
    }
}
