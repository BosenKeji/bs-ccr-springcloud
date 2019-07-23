package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairChoiceAttributeCustomMapper;
import cn.bosenkeji.service.CoinPairChoiceAttributeCustomService;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return Optional.ofNullable(coinPairChoiceAttributeCustomMapper.selectByPrimaryKey(id));
    }

    @Override
    public Optional<CoinPairChoiceAttributeCustom> getByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return Optional.ofNullable(coinPairChoiceAttributeCustomMapper.selectByCoinPartnerChoiceId(coinPartnerChoiceId));
    }

    @Override
    public Optional<Integer> update(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        return Optional.ofNullable(coinPairChoiceAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoiceAttributeCustom));
    }

    @Override
    public Optional<Integer> add(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        return Optional.ofNullable(coinPairChoiceAttributeCustomMapper.insertSelective(coinPairChoiceAttributeCustom));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(coinPairChoiceAttributeCustomMapper.deleteByPrimaryKey(id));
    }


}
