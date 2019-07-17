package cn.bosenkeji.service;

import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;



public interface CoinPairDealService {

    PageInfo<CoinPairDealVO> findCoinPairDealByUserId(Integer userId , Integer pageNum , Integer pageSize);

    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(Integer userId, Integer choicId, Integer pageNum, Integer pageSize);

    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndStatus(Integer userId, Integer status, Integer pageNum, Integer pageSize);

    boolean updataCoinPairDealStartsById(Integer id, Integer status);

    int countCoinPair(Integer userId);

    int countCoinPairDeal(Integer userId, Integer choicId);

}
