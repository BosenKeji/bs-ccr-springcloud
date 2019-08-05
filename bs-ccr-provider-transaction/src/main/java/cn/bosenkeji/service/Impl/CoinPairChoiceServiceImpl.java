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

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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




    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int userId,int coinId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(fill( userId, coinId));
    }

    private List<CoinPairChoice> fill(int userId,int coinId) {
        List<CoinPairChoice>  coinPairChoices = coinPairChoiceMapper.findAllByUserId(userId);
        if (!coinPairChoices.isEmpty()){
            for (CoinPairChoice c : coinPairChoices) {
                List<CoinPairCoin> coinPairCoins = this.iCoinPairCoinClientService.listByCoinId(coinId);
                if (!coinPairCoins.isEmpty()){
                    fill(c,coinPairCoins);
                }
            }

        }
        return coinPairChoices;
    }

    private void fill(CoinPairChoice coinPairChoice, List<CoinPairCoin> coinPairCoins) {
        for (CoinPairCoin coinPairCoin : coinPairCoins) {
            if (coinPairCoin.getCoinPairId() == coinPairChoice.getCoinPartnerId()){
                CoinPair coinPair = this.iCoinPairClientService.getCoinPair(coinPairCoin.getCoinPairId());
                coinPairChoice.setCoinPair(coinPair);
            }
        }
    }

    @Override
    public Optional<CoinPairChoice> get(int id) {
        CoinPairChoice coinPairChoice = this.coinPairChoiceMapper.selectByPrimaryKey(id);
        if (coinPairChoice != null){
            CoinPair coinPair = this.iCoinPairClientService.getCoinPair(coinPairChoice.getCoinPartnerId());
            coinPairChoice.setCoinPair(coinPair);
        }
        return Optional.ofNullable(coinPairChoice);
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
    public List<CoinPairChoiceJoinCoinPair> listCoinPairChoice() {
        return coinPairChoiceMapper.listCoinPairChoice();
    }
}
