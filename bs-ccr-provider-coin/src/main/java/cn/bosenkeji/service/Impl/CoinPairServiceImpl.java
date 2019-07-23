package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairMapper;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:12
 */
@Service
public class CoinPairServiceImpl implements CoinPairService {

    @Resource
    CoinPairMapper coinPairMapper;

    @Override
    public List<CoinPair> list() {
        return coinPairMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(list());
    }

    @Override
    public Optional<CoinPair> get(int id) {
        return Optional.ofNullable(coinPairMapper.selectByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> add(CoinPair coinPair) {
        return Optional.ofNullable(coinPairMapper.insertSelective(coinPair));
    }

    @Override
    public Optional<Integer> update(CoinPair coinPair) {
        return Optional.ofNullable(coinPairMapper.updateByPrimaryKeySelective(coinPair));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(coinPairMapper.deleteByPrimaryKey(id));
    }
}
