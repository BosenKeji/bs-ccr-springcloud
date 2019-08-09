package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairChoiceMapper;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import cn.bosenkeji.vo.transaction.CoinPairChoiceJoinCoinPair;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author CAJR
 * @create 2019/7/17 16:07
 */
@Service
public class CoinPairChoiceServiceImpl implements CoinPairChoiceService {

    @Resource
    CoinPairChoiceMapper coinPairChoiceMapper;

    @Autowired
    ICoinPairCoinClientService iCoinPairCoinClientService;

    @Autowired
    ICoinPairClientService iCoinPairClientService;

    @Autowired
    ICoinPairClientService coinPairClientService;


    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int userId,int coinId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(fill( userId, coinId));
    }

    private List<CoinPairChoice> fill(int userId,int coinId) {
        //货币对Map
        Map<Integer, CoinPair> coinPairMap = new HashMap<>();
        //根据userId查询自选币list
        List<CoinPairChoice>  coinPairChoices = coinPairChoiceMapper.findAllByUserId(userId);
        //根据货币id查询货币对货币的列表
        List<CoinPairCoin> coinPairCoinList = this.iCoinPairCoinClientService.listByCoinId(coinId);

        //获取货币对的id的list
        List<Integer> coinPairIds = new ArrayList<>();
        if (!coinPairCoinList.isEmpty()){
            for (CoinPairCoin c : coinPairCoinList) {
                coinPairIds.add(c.getCoinPairId());
            }
        }

        //根据货币对id列表的填充coinPairMap
        List<CoinPair> coinPairs = this.iCoinPairClientService.findSection(coinPairIds);
        if (!coinPairs.isEmpty()){
            for (CoinPair c : coinPairs) {
                coinPairMap.put(c.getId(), c);
            }
        }

        //填充自选币的货币对数据
        if (!coinPairChoices.isEmpty()){
            for (CoinPairChoice c : coinPairChoices) {
                fill(c,coinPairMap);
            }

        }
        return coinPairChoices;
    }

    private void fill(CoinPairChoice c, Map<Integer, CoinPair> coinPairMap) {
        if (coinPairMap.containsKey(c.getCoinPartnerId())){
            c.setCoinPair(coinPairMap.get(c.getCoinPartnerId()));
        }
    }


    @Override
    public CoinPairChoice get(int id) {
        CoinPairChoice coinPairChoice = this.coinPairChoiceMapper.selectByPrimaryKey(id);
        if (coinPairChoice != null){
            CoinPair coinPair = this.iCoinPairClientService.getCoinPair(coinPairChoice.getCoinPartnerId());
            coinPairChoice.setCoinPair(coinPair);
        }
        return coinPairChoice;
    }

    @Override
    public Optional<Integer> add(CoinPairChoice coinPairChoice) {
        return Optional.ofNullable(coinPairChoiceMapper.insertSelective(coinPairChoice));
    }

    @Override
    public Optional<Integer> update(CoinPairChoice coinPairChoice) {
        return Optional.ofNullable(coinPairChoiceMapper.updateByPrimaryKeySelective(coinPairChoice));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(coinPairChoiceMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkExistByCoinPartnerNameAndUserId(String coinPairName, int userId) {
        CoinPair coinPair = this.iCoinPairClientService.getCoinPairByName(coinPairName);
        return Optional.ofNullable(this.coinPairChoiceMapper.checkExistByCoinPartnerIdAndUserId(coinPair.getId(), userId));
    }

    @Override
    public Optional<Integer> checkExistByCoinPartnerIdAndUserId(int coinPairId, int userId) {
        return Optional.ofNullable(this.coinPairChoiceMapper.checkExistByCoinPartnerIdAndUserId(coinPairId, userId));
    }

    @Override
    public List<CoinPairChoice> findAll() {
        List<CoinPairChoice> coinPairChoiceList = coinPairChoiceMapper.findAll();
        List<CoinPair> coinPairList = coinPairClientService.findAll();
        if (!CollectionUtils.isEmpty(coinPairChoiceList) && !CollectionUtils.isEmpty(coinPairList)) {
            for (CoinPairChoice c : coinPairChoiceList) {
                Optional<CoinPair> first = coinPairList.stream().filter((v) -> v.getId() == c.getCoinPartnerId()).findFirst();
                c.setCoinPair(first.get());
            }
        }
        return coinPairChoiceList;
    }
}
