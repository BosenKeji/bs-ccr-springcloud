package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairDealMapper;
import cn.bosenkeji.service.CoinPairDealService;
import cn.bosenkeji.vo.transaction.CoinPairDeal;
import cn.bosenkeji.vo.transaction.CoinPairDealOther;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CoinPairDealServiceImpl implements CoinPairDealService {

    @Autowired
    private CoinPairDealMapper coinPairDealMapper;

    @Override
    public Optional<Integer> insertCoinPairDealBySelective(CoinPairDeal coinPairDeal) {
        coinPairDeal.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairDeal.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        int result = coinPairDealMapper.insertSelective(coinPairDeal);
        return Optional.of(result);
    }


    @Override

    public PageInfo<CoinPairDealOther> findCoinPairDealByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CoinPairDeal> list = coinPairDealMapper.findCoinPairDealByUserId(userId);
        List<CoinPairDealOther> voList = new ArrayList<>();
        for (CoinPairDeal c : list) {
            voList.add(convertCoinPairDealOther(c));
        }
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<CoinPairDealOther> findCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CoinPairDeal> list = coinPairDealMapper.findCoinPairDealByUserIdAndChoiceId(userId,choiceId);
        List<CoinPairDealOther> voList = new ArrayList<>();
        for (CoinPairDeal c: list) {
            voList.add(convertCoinPairDealOther(c));
        }
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<CoinPairDealOther> findCoinPairDealByUserIdAndType(Integer userId, Integer type, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CoinPairDeal> list = coinPairDealMapper.findCoinPairDealByUserIdAndType(userId,type);
        List<CoinPairDealOther> voList = new ArrayList<>();
        for (CoinPairDeal c: list) {
            voList.add(convertCoinPairDealOther(c));
        }
        return new PageInfo<>(voList);
    }

    @Override
    public Optional<Integer> updateCoinPairDealStartsById(Integer id, Integer status) {
        CoinPairDeal c = new CoinPairDeal();
        c.setId(id);
        c.setStatus(status);
        c.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        int i = coinPairDealMapper.updateByPrimaryKeySelective(c);
        return Optional.of(i);
    }

    @Override
    public Optional<Integer> countCoinPair(Integer userId) {
        return Optional.of(coinPairDealMapper.countCoinPair(userId));
    }

    @Override
    public Optional<Integer> countCoinPairDeal(Integer userId, Integer choiceId) {
        return Optional.of(coinPairDealMapper.countCoinPairDeal(userId,choiceId));
    }

    @Override
    public Optional<Integer> deleteCoinPairDealByPrimaryKey(Integer id) {
        return Optional.of(coinPairDealMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> deleteBatchCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId) {
        return Optional.of(coinPairDealMapper.deleteBatchCoinPairDealByUserIdAndChoiceId(userId,choiceId));
    }

    private CoinPairDealOther convertCoinPairDealOther(CoinPairDeal coinPairDeal) {
        CoinPairDealOther other = new CoinPairDealOther();
        other.setId(coinPairDeal.getId());
        other.setCoinPartnerChoicId(coinPairDeal.getCoinPartnerChoiceId());
        other.setType(coinPairDeal.getType());
        other.setQuantity(coinPairDeal.getQuantity());
        other.setStatus(coinPairDeal.getStatus());
        return other;
    }

}
