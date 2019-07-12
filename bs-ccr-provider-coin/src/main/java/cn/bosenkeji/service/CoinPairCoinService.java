package cn.bosenkeji.service;

import cn.bosenkeji.vo.CoinPairCoin;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:08
 */
public interface CoinPairCoinService {
    List<CoinPairCoin> list();

    Optional<CoinPairCoin> get(int id);

    boolean add(CoinPairCoin coinPairCoin);

    boolean update(CoinPairCoin coinPairCoin);

    boolean delete(int id);
}
