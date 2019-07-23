package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.CoinPairChoiceMapper;
import cn.bosenkeji.service.CoinPairChoicService;
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
public class CoinPairChoiceServiceImpl implements CoinPairChoicService {

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
    public boolean add(CoinPairChoice coinPairChoice) {
        return coinPairChoiceMapper.insertSelective(coinPairChoice) == 1;
    }

    @Override
    public boolean update(CoinPairChoice coinPairChoice) {
        return coinPairChoiceMapper.updateByPrimaryKeySelective(coinPairChoice) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinPairChoiceMapper.deleteByPrimaryKey(id) == 1;
    }
}
