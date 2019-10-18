package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 17:21
 */
public interface CoinPairChoiceAttributeCustomService {
    public static final int PROFIT_TRACE_TYPE = 1;
    public static final int PROFIT_FIXED_TYPE = 2;

    CoinPairChoiceAttributeCustom get(int id);

    CoinPairChoiceAttributeCustom getByCoinPartnerChoiceId(int coinPartnerChoiceId);

    Optional<Integer> update(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom);

    Optional<Integer> updateByCoinPairChoiceId(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom);

    Optional<Integer> add(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom);

    Optional<Integer> delete(int id);

    Optional<Integer> deleteByCoinPairChoiceId(int coinPairChoiceId);

    Optional<Integer> batchDelete(List<Integer> coinPartnerChoiceIds);

    Optional<Integer> checkByCoinPartnerChoiceId(int coinPartnerChoiceId);
}
