package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;

import java.util.Optional;


public interface CoinPairDealService {

    Optional<Integer> insertCoinPairDealBySelective(CoinPairDeal coinPairDeal);

    PageInfo<CoinPairDealVO> findCoinPairDealByUserId(Integer userId , Integer pageNum , Integer pageSize);

    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId, Integer pageNum, Integer pageSize);

    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(Integer userId, Integer type, Integer pageNum, Integer pageSize);

    Optional<Integer> updateCoinPairDealStartsById(Integer id, Integer status);

    Optional<Integer> countCoinPair(Integer userId);

<<<<<<< HEAD
    Optional<Integer> countCoinPairDeal(Integer userId, Integer choiceId);
=======
    int countCoinPairDeal(Integer userId, Integer choiceId);
>>>>>>> 44f17741ddcf02033387e1bc9c08f7f89b1e11f0

    Optional<Integer> deleteCoinPairDealByPrimaryKey(Integer id);

<<<<<<< HEAD
    Optional<Integer> deleteBatchCoinPairDealByUserIdAndChoiceId(Integer userId,Integer choiceId);
=======
    boolean deleteBatchCoinPairDealByUserIdAndChoiceId(Integer userId,Integer choicId);
>>>>>>> 44f17741ddcf02033387e1bc9c08f7f89b1e11f0

}
