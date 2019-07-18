package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairChoicAttributeMapper;
import cn.bosenkeji.service.CoinPairChoicAttributeService;
import cn.bosenkeji.vo.CoinPairChoicAttribute;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 10:51
 */
@Service
public class CoinPairChoicAttributeServiceImpl implements CoinPairChoicAttributeService {

    @Resource
    CoinPairChoicAttributeMapper coinPairChoicAttributeMapper;

    @Override
    public CoinPairChoicAttribute getByCoinPartnerChoicId(int coinPartnerChoicId) {
        return coinPairChoicAttributeMapper.selectBycoinPartnerChoicId(coinPartnerChoicId);
    }

    @Override
    public Optional<CoinPairChoicAttribute> get(int id) {
        return Optional.ofNullable(coinPairChoicAttributeMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean update(CoinPairChoicAttribute coinPairChoicAttribute) {
        return coinPairChoicAttributeMapper.updateByPrimaryKeySelective(coinPairChoicAttribute) == 1;
    }

    @Override
    public boolean add(CoinPairChoicAttribute coinPairChoicAttribute) {
        return coinPairChoicAttributeMapper.insertSelective(coinPairChoicAttribute) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinPairChoicAttributeMapper.deleteByPrimaryKey(id) ==1;
    }
}
