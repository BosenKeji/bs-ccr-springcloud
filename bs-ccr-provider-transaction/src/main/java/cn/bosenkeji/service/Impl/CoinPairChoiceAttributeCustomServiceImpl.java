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
    public boolean update(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        return coinPairChoiceAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoiceAttributeCustom) == 1;
    }

    @Override
    public boolean add(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        return coinPairChoiceAttributeCustomMapper.insertSelective(coinPairChoiceAttributeCustom) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinPairChoiceAttributeCustomMapper.deleteByPrimaryKey(id) == 1;
    }


}
