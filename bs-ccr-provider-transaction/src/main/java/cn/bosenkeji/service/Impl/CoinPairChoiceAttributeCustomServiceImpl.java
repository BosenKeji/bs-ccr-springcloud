package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairChoiceAttributeCustomMapper;
import cn.bosenkeji.service.CoinPairChoiceAttributeCustomService;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.util.CommonConstantUtil;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static cn.bosenkeji.util.CommonConstantUtil.FAIL;

/**
 * @Author CAJR
 * @create 2019/7/18 17:30
 */
@Service
public class CoinPairChoiceAttributeCustomServiceImpl implements CoinPairChoiceAttributeCustomService {

    @Resource
    CoinPairChoiceAttributeCustomMapper coinPairChoiceAttributeCustomMapper;

    @Resource
    CoinPairChoiceService coinPairChoiceService;

    @Override
    public CoinPairChoiceAttributeCustom get(int id) {
        CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom = this.coinPairChoiceAttributeCustomMapper.selectByPrimaryKey(id);
        if (coinPairChoiceAttributeCustom != null){
            coinPairChoiceAttributeCustom.setStopProfitMoney(coinPairChoiceAttributeCustom.getStopProfitMoney() / CommonConstantUtil.ACCURACY);
            coinPairChoiceAttributeCustom.setFirstOpenPrice(coinPairChoiceAttributeCustom.getFirstOpenPrice() / CommonConstantUtil.ACCURACY_RATIO);
        }
        return coinPairChoiceAttributeCustom;
    }

    @Override
    public CoinPairChoiceAttributeCustom getByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom = this.coinPairChoiceAttributeCustomMapper.selectByCoinPartnerChoiceId(coinPartnerChoiceId);
        if (coinPairChoiceAttributeCustom != null){
            coinPairChoiceAttributeCustom.setStopProfitMoney(coinPairChoiceAttributeCustom.getStopProfitMoney() / CommonConstantUtil.ACCURACY);
            coinPairChoiceAttributeCustom.setFirstOpenPrice(coinPairChoiceAttributeCustom.getFirstOpenPrice() / CommonConstantUtil.ACCURACY_RATIO);
        }
        return coinPairChoiceAttributeCustom;
    }

    @Override
    public Optional<Integer> update(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        int stopProfitType= coinPairChoiceAttributeCustom.getStopProfitType();
        coinPairChoiceAttributeCustom.setStopProfitMoney(coinPairChoiceAttributeCustom.getStopProfitMoney() * CommonConstantUtil.ACCURACY);

        if (this.coinPairChoiceService.get(coinPairChoiceAttributeCustom.getCoinPairChoiceId()) == null){
            return Optional.of(FAIL);
        }

        if (stopProfitType == CoinPairChoiceAttributeCustomService.PROFIT_TRACE_TYPE){
//            coinPairChoiceAttributeCustom.setStopProfitFixedRate(0.00);

            coinPairChoiceAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return Optional.of(this.coinPairChoiceAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoiceAttributeCustom));
        }

        if (stopProfitType == CoinPairChoiceAttributeCustomService.PROFIT_FIXED_TYPE){
//            coinPairChoiceAttributeCustom.setStopProfitTraceTriggerRate(0.00);
//            coinPairChoiceAttributeCustom.setStopProfitTraceDropRate(0.00);

            coinPairChoiceAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return Optional.of(this.coinPairChoiceAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoiceAttributeCustom));

        }

        return Optional.of(FAIL);
    }

    @Override
    public Optional<Integer> updateByCoinPairChoiceId(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        coinPairChoiceAttributeCustom.setStopProfitMoney(coinPairChoiceAttributeCustom.getStopProfitMoney() * CommonConstantUtil.ACCURACY);
        return Optional.of(this.coinPairChoiceAttributeCustomMapper.updateByCoinPartnerChoiceId(coinPairChoiceAttributeCustom));
    }

    @Override
    public Optional<Integer> add(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        coinPairChoiceAttributeCustom.setStopProfitMoney(coinPairChoiceAttributeCustom.getStopProfitMoney() * CommonConstantUtil.ACCURACY);
        if (this.coinPairChoiceService.get(coinPairChoiceAttributeCustom.getCoinPairChoiceId()) == null){
            return Optional.of(FAIL);
        }

        return Optional.of(this.coinPairChoiceAttributeCustomMapper.insertSelective(coinPairChoiceAttributeCustom));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(this.coinPairChoiceAttributeCustomMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> deleteByCoinPairChoiceId(int coinPairChoiceId) {
        return Optional.of(this.coinPairChoiceAttributeCustomMapper.deleteByCoinPairChoiceId(coinPairChoiceId));
    }

    @Override
    public Optional<Integer> batchDelete(List<Integer> coinPartnerChoiceIds) {
        return Optional.of(this.coinPairChoiceAttributeCustomMapper.batchDelete(coinPartnerChoiceIds));
    }

    @Override
    public Optional<Integer> batchSetFirstOpenPrice(Map<Integer, Double> coinPairAndOpenPrice, int tradePlatformApiBindProductComboId) {
        if (coinPairAndOpenPrice.isEmpty()){
            return Optional.empty();
        }
        List<CoinPairChoiceAttributeCustom> coinPairChoiceAttributeCustomsUpdateList = new ArrayList<>();

        for (Map.Entry<Integer, Double> entry : coinPairAndOpenPrice.entrySet()) {
            if (this.coinPairChoiceService.checkExistByCoinPartnerIdAndRobotId(entry.getKey(), tradePlatformApiBindProductComboId).get() > 0){
                CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom = new CoinPairChoiceAttributeCustom();
                coinPairChoiceAttributeCustom.setCoinPairChoiceId(entry.getKey());
                coinPairChoiceAttributeCustom.setFirstOpenPrice(entry.getValue()*CommonConstantUtil.ACCURACY_RATIO);
                coinPairChoiceAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                if (this.coinPairChoiceAttributeCustomMapper.checkByCoinPartnerChoiceId(entry.getKey()) > 0){
                    coinPairChoiceAttributeCustomsUpdateList.add(coinPairChoiceAttributeCustom);
                }else {
                    coinPairChoiceAttributeCustom.setStopProfitFixedRate(0.0);
                    coinPairChoiceAttributeCustom.setStopProfitMoney(0.0);
                    coinPairChoiceAttributeCustom.setStopProfitType(0);
                    coinPairChoiceAttributeCustom.setStopProfitTraceDropRate(0.0);
                    coinPairChoiceAttributeCustom.setStopProfitTraceTriggerRate(0.0);
                    coinPairChoiceAttributeCustom.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    coinPairChoiceAttributeCustom.setStatus(1);
                    add(coinPairChoiceAttributeCustom);
                }
            }
        }
        
        return Optional.of(this.coinPairChoiceAttributeCustomMapper.batchUpdateFirstOpenPrice(coinPairChoiceAttributeCustomsUpdateList));
    }


    @Override
    public Optional<Integer> checkByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.checkByCoinPartnerChoiceId(coinPartnerChoiceId));
    }


}
