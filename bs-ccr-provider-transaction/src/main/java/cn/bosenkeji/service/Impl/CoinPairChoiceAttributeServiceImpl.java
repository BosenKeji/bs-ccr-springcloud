package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairChoiceAttributeMapper;
import cn.bosenkeji.service.CoinPairChoiceAttributeService;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 10:51
 */
@Service
public class CoinPairChoiceAttributeServiceImpl implements CoinPairChoiceAttributeService {

    @Resource
    CoinPairChoiceAttributeMapper coinPairChoiceAttributeMapper;

    @Override
    public CoinPairChoiceAttribute getByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return this.coinPairChoiceAttributeMapper.selectByCoinPartnerChoiceId(coinPartnerChoiceId);
    }

    @Override
    public Optional<CoinPairChoiceAttribute> get(int id) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.selectByCoinPartnerChoiceId(id));
    }

    @Override
    public Optional<Integer> update(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.updateByCoinPartnerChoiceIdSelective(coinPairChoiceAttribute));
    }

    @Override
    public Optional<Integer> add(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.insertSelective(coinPairChoiceAttribute));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.checkByCoinPartnerChoiceId(coinPartnerChoiceId));
    }
}
