package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinMapper;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * @ClassName CoinServiceImpl
 * @Description 货币实现类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinMapper coinMapper;

    @Override
    public List<Coin> list() {
        return coinMapper.findAll();
    }

    @Override
    public Coin get(int id) {
        return coinMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean add(Coin coin) {
        return coinMapper.insert(coin)==1;
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
