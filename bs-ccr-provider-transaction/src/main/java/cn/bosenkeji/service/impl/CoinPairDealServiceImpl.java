package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairDealMapper;
import cn.bosenkeji.service.CoinPairDealService;
import cn.bosenkeji.vo.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
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
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CoinPairDeal> list = coinPairDealMapper.findCoinPairDealByUserId(userId);
        List<CoinPairDealVO> voList = new ArrayList<>();
        for (CoinPairDeal c : list) {
            voList.add(convertCoinPairDealVO(c));
        }
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CoinPairDeal> list = coinPairDealMapper.findCoinPairDealByUserIdAndChoiceId(userId,choiceId);
        List<CoinPairDealVO> voList = new ArrayList<>();
        for (CoinPairDeal c: list) {
            voList.add(convertCoinPairDealVO(c));
        }
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(Integer userId, Integer type, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CoinPairDeal> list = coinPairDealMapper.findCoinPairDealByUserIdAndType(userId,type);
        List<CoinPairDealVO> voList = new ArrayList<>();
        for (CoinPairDeal c: list) {
            voList.add(convertCoinPairDealVO(c));
        }
        return new PageInfo<>(voList);
    }

    @Override
    public Optional<Integer> updateCoinPairDealStartsById(Integer id, Integer status) {
        CoinPairDeal c = new CoinPairDeal();
        c.setId(id);
        c.setStatus(status);
        c.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        int result = coinPairDealMapper.updateByPrimaryKeySelective(c);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> countCoinPair(Integer userId) {
        int result = coinPairDealMapper.countCoinPair(userId);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> countCoinPairDeal(Integer userId, Integer choiceId) {
        int result = coinPairDealMapper.countCoinPairDeal(userId,choiceId);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> deleteCoinPairDealByPrimaryKey(Integer id) {
        int result = coinPairDealMapper.deleteByPrimaryKey(id);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> deleteBatchCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId) {
        int result = coinPairDealMapper.deleteBatchCoinPairDealByUserIdAndChoiceId(userId,choiceId);
        return Optional.of(result);
    }

    private CoinPairDealVO convertCoinPairDealVO(CoinPairDeal coinPairDeal) {
        CoinPairDealVO vo = new CoinPairDealVO();
        vo.setId(coinPairDeal.getId());
        vo.setCoinPartnerChoicId(coinPairDeal.getCoinPartnerChoiceId());
        vo.setType(coinPairDeal.getType());
        vo.setQuantity(coinPairDeal.getQuantity());
        vo.setStatus(coinPairDeal.getStatus());
        return vo;
    }

}
