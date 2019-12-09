package cn.bosenkeji;

import cn.bosenkeji.exception.enums.CoinEnum;
import cn.bosenkeji.mapper.CoinPairCoinMapper;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.coin.Coin;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/9 17:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testCoin {

    @Autowired
    CoinService coinService;
    @Autowired
    CoinPairService coinPairService;
    @Autowired
    CoinPairCoinMapper coinPairCoinMapper;

    @Test
    public void findErrorRecord(){
        List<CoinPair> coinPairs = this.coinPairService.list();
        for (CoinPair coinPair : coinPairs) {
            List<CoinPairCoin> coinPairCoins = this.coinPairCoinMapper.findAllByCoinPairId(coinPair.getId());
            if (!CollectionUtils.isEmpty(coinPairCoins)){
                String coinPairName = coinPair.getName();
                for (CoinPairCoin coinPairCoin : coinPairCoins) {
                    String coinName = "";
                    Coin coin = this.coinService.get(coinPairCoin.getCoinId());
                    if (coin != null){
                        coinName = coin.getName();
                    }
                    int coinNameLength = coinName.length();
                    int coinPairNameLength = coinPairName.length();
                    if (coinPairName.lastIndexOf(coinName)+coinNameLength != coinPairNameLength){
                        System.out.println("coinName ==>"+coinName);
                        System.out.println("coinPairName ==>"+coinPairName);
                        System.out.println("coinPairCoin id ==>"+coinPairCoin.getId());
                    }
                }
            }
        }
    }
}
