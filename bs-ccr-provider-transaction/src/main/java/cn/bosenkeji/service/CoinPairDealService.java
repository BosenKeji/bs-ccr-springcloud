package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairDeal;
import cn.bosenkeji.vo.transaction.CoinPairDealOther;
import com.github.pagehelper.PageInfo;

import java.util.Optional;


public interface CoinPairDealService {

    Optional<Integer> insertCoinPairDealBySelective(CoinPairDeal coinPairDeal);

    PageInfo<CoinPairDealOther> findCoinPairDealByUserId(Integer userId , Integer pageNum , Integer pageSize);

    PageInfo<CoinPairDealOther> findCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId, Integer pageNum, Integer pageSize);

    PageInfo<CoinPairDealOther> findCoinPairDealByUserIdAndType(Integer userId, Integer type, Integer pageNum, Integer pageSize);

    Optional<Integer> updateCoinPairDealStartsById(Integer id, Integer status);

    Optional<Integer> countCoinPair(Integer userId);

    Optional<Integer> countCoinPairDeal(Integer userId, Integer choiceId);


    Optional<Integer> deleteCoinPairDealByPrimaryKey(Integer id);

    Optional<Integer> deleteBatchCoinPairDealByUserIdAndChoiceId(Integer userId,Integer choiceId);


}
