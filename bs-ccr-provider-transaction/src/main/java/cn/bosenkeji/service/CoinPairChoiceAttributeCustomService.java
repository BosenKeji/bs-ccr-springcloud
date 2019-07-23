package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 17:21
 */
public interface CoinPairChoiceAttributeCustomService {

    Optional<CoinPairChoiceAttributeCustom> get(int id);

    Optional<CoinPairChoiceAttributeCustom> getByCoinPartnerChoiceId(int coinPartnerChoiceId);

    Optional<Integer> update(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom);

    Optional<Integer> add(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom);

    Optional<Integer> delete(int id);
}
