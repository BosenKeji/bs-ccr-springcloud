package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairCoinMapper;
import cn.bosenkeji.service.CoinPairCoinService;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.coin.Coin;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import cn.bosenkeji.vo.coin.CoinPairCoinResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Resource
    CoinPairService coinPairService;

    @Resource
    CoinService coinService;

    @Override
    public List<CoinPairCoin> list() {
        return coinPairCoinMapper.findAll();
    }

    @Override
    public List<CoinPairCoin> listByCoinId(int coinId) {
        return this.coinPairCoinMapper.findAllByCoinId(coinId);
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(list());
    }

    @Override
    public CoinPairCoin get(int id) {
        return coinPairCoinMapper.selectByPrimaryKey(id);
    }

    @Override
    public Optional<Integer> add(CoinPairCoin coinPairCoin) {
        return Optional.of(coinPairCoinMapper.insertSelective(coinPairCoin));
    }

    @Override
    public Optional<Integer> update(CoinPairCoin coinPairCoin) {
        return Optional.of(coinPairCoinMapper.updateByPrimaryKeySelective(coinPairCoin));
    }

    @Override
    public Optional<Integer> delete(int coinId, int coinPairId) {
        return Optional.of(coinPairCoinMapper.deleteByCoinIdAndCoinPairId(coinId, coinPairId));
    }

    @Override
    public Optional<Integer> checkByCoinIdAndCoinPairId(int coinId, int coinPairId) {
        return Optional.ofNullable(this.coinPairCoinMapper.checkByCoinIdAndCoinPairId(coinId, coinPairId));
    }

    @Override
    public CoinPairCoinResult findBaseCoin(String coinPairName) {
        coinPairName = coinPairName.toLowerCase();
        if (coinPairName.contains("-")){
            coinPairName = coinPairName.replaceAll("-","").trim();
        }

        CoinPairCoinResult result = new CoinPairCoinResult();
        CoinPair coinPair = this.coinPairService.getByName(coinPairName);
        if (coinPair == null){
            return result;
        }
        result.setCoinPairName(coinPair.getName());
        List<CoinPairCoin> coinPairCoins = this.coinPairCoinMapper.findAllByCoinPairId(coinPair.getId());
        if (!CollectionUtils.isEmpty(coinPairCoins)){
            for (CoinPairCoin coinPairCoin : coinPairCoins) {
                String coinName = "";
                Coin coin = this.coinService.get(coinPairCoin.getCoinId());
                if (coin != null){
                    coinName = coin.getName();
                }
                int coinNameLength = coinName.length();
                int coinPairNameLength = coinPairName.length();
                if (coinPairName.lastIndexOf(coinName)+coinNameLength != coinPairNameLength){
                    result.setBaseCoinName(coinName);
                }else {
                    result.setValuationCoinName(coinName);
                }
            }
        }
        if (result.getBaseCoinName() == null && result.getValuationCoinName() != null){
            result.setBaseCoinName(coinPairName.replace(result.getValuationCoinName(),"").trim());
        }

        return result;
    }


}
