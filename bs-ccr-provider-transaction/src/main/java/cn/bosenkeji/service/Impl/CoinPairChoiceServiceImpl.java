package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairChoiceMapper;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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


    @Override
    public List<CoinPairChoice> list() {
        return coinPairChoiceMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(list());
    }

    @Override
    public Optional<CoinPairChoice> get(int id) {
        return Optional.ofNullable(coinPairChoiceMapper.selectByPrimaryKey(id));
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
    public Optional<Integer> checkExistByCoinPartnerIdAndUserId(int coinPartnerId, int userId) {
        return Optional.ofNullable(this.coinPairChoiceMapper.checkExistByCoinPartnerIdAndUserId(coinPartnerId, userId));
    }
}
