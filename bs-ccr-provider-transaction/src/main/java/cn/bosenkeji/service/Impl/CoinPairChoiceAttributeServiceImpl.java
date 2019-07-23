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
        return coinPairChoiceAttributeMapper.selectByCoinPartnerChoiceId(coinPartnerChoiceId);
    }

    @Override
    public Optional<CoinPairChoiceAttribute> get(int id) {
        return Optional.ofNullable(coinPairChoiceAttributeMapper.selectByCoinPartnerChoiceId(id));
    }

    @Override
    public boolean update(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        return coinPairChoiceAttributeMapper.updateByCoinPartnerChoiceIdSelective(coinPairChoiceAttribute) == 1;
    }

    @Override
    public boolean add(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        return coinPairChoiceAttributeMapper.insertSelective(coinPairChoiceAttribute) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinPairChoiceAttributeMapper.deleteByPrimaryKey(id) ==1;
    }
}
