package cn.bosenkeji.service;

import cn.bosenkeji.vo.CoinPairChoicAttribute;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/17 10:58
 */
public interface CoinPairChoicAttributeService {

    CoinPairChoicAttribute getByCoinPartnerChoicId(int coinPartnerChoicId);

    Optional<CoinPairChoicAttribute> get(int id);

    boolean update(CoinPairChoicAttribute coinPairChoicAttribute);

    boolean add(CoinPairChoicAttribute coinPairChoicAttribute);

    boolean delete(int id);

}
