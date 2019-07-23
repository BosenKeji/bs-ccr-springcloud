package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;



public interface CoinPairDealService {

    boolean insertCoinPairDealBySelective(CoinPairDeal coinPairDeal);

    PageInfo<CoinPairDealVO> findCoinPairDealByUserId(Integer userId , Integer pageNum , Integer pageSize);

    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId, Integer pageNum, Integer pageSize);

    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(Integer userId, Integer type, Integer pageNum, Integer pageSize);

    boolean updateCoinPairDealStartsById(Integer id, Integer status);

    int countCoinPair(Integer userId);

    int countCoinPairDeal(Integer userId, Integer choiceId);

    boolean deleteCoinPairDealByPrimaryKey(Integer id);

    boolean deleteBatchCoinPairDealByUserIdAndChoiceId(Integer userId,Integer choicId);

}
