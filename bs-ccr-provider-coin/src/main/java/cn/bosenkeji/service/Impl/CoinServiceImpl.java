package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinMapper;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.Coin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CoinServiceImpl
 * @Description 货币实现类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@Service
public class CoinServiceImpl implements CoinService {

    @Resource
    private CoinMapper coinMapper;

    @Override
    public List<Coin> list() {
        return coinMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(list());
    }

    @Override
    public Optional<Coin> get(int id) {
        return Optional.ofNullable(coinMapper.selectByPrimaryKey(id)) ;
    }

    @Override
    public Optional<Integer> add(Coin coin) {
        return Optional.ofNullable(coinMapper.insert(coin)) ;
//        return coinMapper.insert(coin)==1;
    }

    @Override
    public boolean update(Coin coin) {
        return coinMapper.updateByPrimaryKeySelective(coin)==1;
    }

    @Override
    public boolean delete(int id) {
        return coinMapper.deleteByPrimaryKey(id)==1;
    }
}
