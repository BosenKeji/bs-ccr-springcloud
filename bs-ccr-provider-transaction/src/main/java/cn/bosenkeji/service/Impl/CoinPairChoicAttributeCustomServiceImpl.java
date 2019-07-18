package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairChoicAttributeCustomMapper;
import cn.bosenkeji.service.CoinPairChoicAttributeCustomService;
import cn.bosenkeji.vo.CoinPairChoicAttributeCustom;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 17:30
 */
@Service
public class CoinPairChoicAttributeCustomServiceImpl implements CoinPairChoicAttributeCustomService{
    @Resource
    CoinPairChoicAttributeCustomMapper coinPairChoicAttributeCustomMapper;

    @Override
    public Optional<CoinPairChoicAttributeCustom> get(int id) {
        return Optional.ofNullable(coinPairChoicAttributeCustomMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean update(CoinPairChoicAttributeCustom coinPairChoicAttributeCustom) {
        return coinPairChoicAttributeCustomMapper.updateByPrimaryKeySelective(coinPairChoicAttributeCustom) == 1;
    }

    @Override
    public boolean add(CoinPairChoicAttributeCustom coinPairChoicAttributeCustom) {
        return coinPairChoicAttributeCustomMapper.insertSelective(coinPairChoicAttributeCustom) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinPairChoicAttributeCustomMapper.deleteByPrimaryKey(id) == 1;
    }


}
