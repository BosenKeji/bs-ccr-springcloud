package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/17 10:58
 */
public interface CoinPairChoiceAttributeService {

    CoinPairChoiceAttribute getByCoinPartnerChoiceId(int coinPartnerChoiceId);

    CoinPairChoiceAttribute get(int id);

    Optional<Integer> update(CoinPairChoiceAttribute coinPairChoiceAttribute);

    Optional<Integer> add(CoinPairChoiceAttribute coinPairChoiceAttribute);

    Optional<Integer> delete(int id);

    Optional<Integer> checkByCoinPartnerChoiceId(int coinPartnerChoiceId);

    Optional<Integer> setting(String coinPairChoiceIdStr,int strategyId,int money ,int isCustom);



}
