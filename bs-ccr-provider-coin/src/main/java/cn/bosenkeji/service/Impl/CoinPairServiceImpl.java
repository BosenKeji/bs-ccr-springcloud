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
    public List<CoinPair> listSection(List<Integer> ids) {
        return this.coinPairMapper.findSectionByIds(ids);
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(list());
    }

    @Override
    public CoinPair get(int id) {
        return coinPairMapper.selectByPrimaryKey(id);
    }

    @Override
    public CoinPair getByName(String name) {
        return coinPairMapper.selectByName(name);
    }

    @Override
    public Optional<Integer> add(CoinPair coinPair) {
        String coinPairName = coinPair.getName();
        if (coinPairName.contains("-")){
            coinPairName = coinPairName.replaceAll("-","").trim();
            coinPair.setName(coinPairName.toLowerCase());
        }

        if (this.coinPairMapper.checkExistByName(coinPairName) >= 1){
            CoinPair coinPair1 = this.coinPairMapper.selectByName(coinPairName);
            int coinPairId = coinPair1.getId();
            return Optional.of(0-coinPairId);
        }
        coinPairMapper.insertSelective(coinPair);
        return Optional.of(coinPair.getId());
    }

    @Override
    public Optional<Integer> update(CoinPair coinPair) {
        return Optional.of(coinPairMapper.updateByPrimaryKeySelective(coinPair));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(coinPairMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkExistByName(String name) {
        return Optional.ofNullable(this.coinPairMapper.checkExistByName(name));
    }
}
