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
import java.util.List;
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
        }
        return coinPairChoiceAttributeCustom;
    }

    @Override
    public CoinPairChoiceAttributeCustom getByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom = this.coinPairChoiceAttributeCustomMapper.selectByCoinPartnerChoiceId(coinPartnerChoiceId);
        if (coinPairChoiceAttributeCustom != null){
            coinPairChoiceAttributeCustom.setStopProfitMoney(coinPairChoiceAttributeCustom.getStopProfitMoney() / CommonConstantUtil.ACCURACY);
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
            return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoiceAttributeCustom));
        }

        if (stopProfitType == CoinPairChoiceAttributeCustomService.PROFIT_FIXED_TYPE){
//            coinPairChoiceAttributeCustom.setStopProfitTraceTriggerRate(0.00);
//            coinPairChoiceAttributeCustom.setStopProfitTraceDropRate(0.00);

            coinPairChoiceAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoiceAttributeCustom));

        }

        return Optional.of(FAIL);
    }

    @Override
    public Optional<Integer> updateByCoinPairChoiceId(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        coinPairChoiceAttributeCustom.setStopProfitMoney(coinPairChoiceAttributeCustom.getStopProfitMoney() * CommonConstantUtil.ACCURACY);
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.updateByCoinPartnerChoiceId(coinPairChoiceAttributeCustom));
    }

    @Override
    public Optional<Integer> add(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        coinPairChoiceAttributeCustom.setStopProfitMoney(coinPairChoiceAttributeCustom.getStopProfitMoney() * CommonConstantUtil.ACCURACY);
        if (this.coinPairChoiceService.get(coinPairChoiceAttributeCustom.getCoinPairChoiceId()) == null){
            return Optional.of(FAIL);
        }

        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.insertSelective(coinPairChoiceAttributeCustom));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> deleteByCoinPairChoiceId(int coinPairChoiceId) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.deleteByCoinPairChoiceId(coinPairChoiceId));
    }

    @Override
    public Optional<Integer> batchDelete(List<Integer> coinPartnerChoiceIds) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.batchDelete(coinPartnerChoiceIds));
    }


    @Override
    public Optional<Integer> checkByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.checkByCoinPartnerChoiceId(coinPartnerChoiceId));
    }


}
