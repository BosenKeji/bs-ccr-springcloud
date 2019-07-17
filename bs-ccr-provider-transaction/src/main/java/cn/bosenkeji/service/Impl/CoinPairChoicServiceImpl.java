package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairChoicMapper;
import cn.bosenkeji.service.CoinPairChoicService;
import cn.bosenkeji.vo.CoinPairChoic;
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
public class CoinPairChoicServiceImpl implements CoinPairChoicService {

    @Resource
    CoinPairChoicMapper coinPairChoicMapper;


    @Override
    public List<CoinPairChoic> list() {
        return coinPairChoicMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(list());
    }

    @Override
    public Optional<CoinPairChoic> get(int id) {
        return Optional.ofNullable(coinPairChoicMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean add(CoinPairChoic coinPairChoic) {
        return coinPairChoicMapper.insertSelective(coinPairChoic) == 1;
    }

    @Override
    public boolean update(CoinPairChoic coinPairChoic) {
        return coinPairChoicMapper.updateByPrimaryKeySelective(coinPairChoic) == 1;
    }

    @Override
    public boolean delete(int id) {
        return coinPairChoicMapper.deleteByPrimaryKey(id) == 1;
    }
}
