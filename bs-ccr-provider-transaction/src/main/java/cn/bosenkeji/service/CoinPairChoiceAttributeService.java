package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/17 10:58
 */
public interface CoinPairChoiceAttributeService {

    CoinPairChoiceAttribute getByCoinPartnerChoiceId(int coinPartnerChoiceId);

    Optional<CoinPairChoiceAttribute> get(int id);

    boolean update(CoinPairChoiceAttribute coinPairChoiceAttribute);

    boolean add(CoinPairChoiceAttribute coinPairChoiceAttribute);

    boolean delete(int id);

}
