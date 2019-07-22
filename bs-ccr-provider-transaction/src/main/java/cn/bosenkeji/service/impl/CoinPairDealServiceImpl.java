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


@Service
public class CoinPairDealServiceImpl implements CoinPairDealService {

    @Autowired
    private CoinPairDealMapper coinPairDealMapper;

    @Override
    public boolean insertCoinPairDealBySelective(CoinPairDeal coinPairDeal) {
        coinPairDeal.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairDeal.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        int result = coinPairDealMapper.insertSelective(coinPairDeal);
        boolean b = false;
        if (result > 0) {
            b = true;
        }
        return b;
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
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(Integer userId, Integer choicId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CoinPairDeal> list = coinPairDealMapper.findCoinPairDealByUserIdAndChoicId(userId,choicId);
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
    public boolean updateCoinPairDealStartsById(Integer id, Integer status) {
        CoinPairDeal c = new CoinPairDeal();
        c.setId(id);
        c.setStatus(status);
        c.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        int i = coinPairDealMapper.updateByPrimaryKeySelective(c);
        return checkIntResult(i);
    }

    @Override
    public int countCoinPair(Integer userId) {
        return coinPairDealMapper.countCoinPair(userId);
    }

    @Override
    public int countCoinPairDeal(Integer userId, Integer choicId) {
        return coinPairDealMapper.countCoinPairDeal(userId,choicId);
    }

    @Override
    public boolean deleteCoinPairDealByPrimaryKey(Integer id) {
        return checkIntResult(coinPairDealMapper.deleteByPrimaryKey(id));
    }

    @Override
    public boolean deleteBatchCoinPairDealByUserIdAndChoicId(Integer userId, Integer choicId) {
        return checkIntResult(coinPairDealMapper.deleteBatchCoinPairDealByUserIdAndChoicId(userId,choicId));
    }

    private CoinPairDealVO convertCoinPairDealVO(CoinPairDeal coinPairDeal) {
        CoinPairDealVO vo = new CoinPairDealVO();
        vo.setId(coinPairDeal.getId());
        vo.setCoinPartnerChoicId(coinPairDeal.getCoinPartnerChoicId());
        vo.setType(coinPairDeal.getType());
        vo.setQuantity(coinPairDeal.getQuantity());
        vo.setStatus(coinPairDeal.getStatus());
        return vo;
    }

    private boolean checkIntResult(Integer result) {
        boolean b = false;
        if (result > 0) {
            b = true;
        }
        return b;
    }
}
