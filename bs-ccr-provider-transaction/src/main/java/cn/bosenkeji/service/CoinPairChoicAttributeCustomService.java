package cn.bosenkeji.service;

import cn.bosenkeji.vo.CoinPairChoicAttributeCustom;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 17:21
 */
public interface CoinPairChoicAttributeCustomService {

    Optional<CoinPairChoicAttributeCustom> get(int id);

    boolean update(CoinPairChoicAttributeCustom coinPairChoicAttributeCustom);

    boolean add(CoinPairChoicAttributeCustom coinPairChoicAttributeCustom);

    boolean delete(int id);
}
