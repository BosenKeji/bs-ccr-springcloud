package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairChoiceAttributeCustomMapper;
import cn.bosenkeji.service.CoinPairChoiceAttributeCustomService;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 17:30
 */
@Service
public class CoinPairChoiceAttributeCustomServiceImpl implements CoinPairChoiceAttributeCustomService {

    @Resource
    CoinPairChoiceAttributeCustomMapper coinPairChoiceAttributeCustomMapper;

    @Override
    public Optional<CoinPairChoiceAttributeCustom> get(int id) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.selectByPrimaryKey(id));
    }

    @Override
    public Optional<CoinPairChoiceAttributeCustom> getByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.selectByCoinPartnerChoiceId(coinPartnerChoiceId));
    }

    @Override
    public Optional<Integer> update(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        int stopProfitType= coinPairChoiceAttributeCustom.getStopProfitType();

        if (stopProfitType == CoinPairChoiceAttributeCustomService.PROFIT_TRACE_TYPE){
            coinPairChoiceAttributeCustom.setStopProfitFixedRate(0.00);

            coinPairChoiceAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoiceAttributeCustom));
        }

        if (stopProfitType == CoinPairChoiceAttributeCustomService.PROFIT_FIXED_TYPE){
            coinPairChoiceAttributeCustom.setStopProfitTraceTriggerRate(0.00);
            coinPairChoiceAttributeCustom.setStopProfitTraceDropRate(0.00);

            coinPairChoiceAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoiceAttributeCustom));

        }

        return Optional.ofNullable(0);
    }

    @Override
    public Optional<Integer> updateByCoinPairChoiceId(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.updateByCoinPartnerChoiceId(coinPairChoiceAttributeCustom));
    }

    @Override
    public Optional<Integer> add(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.insertSelective(coinPairChoiceAttributeCustom));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return Optional.ofNullable(this.coinPairChoiceAttributeCustomMapper.checkByCoinPartnerChoiceId(coinPartnerChoiceId));
    }


}
