package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinMapper;
import cn.bosenkeji.service.ICoinService;
import cn.bosenkeji.vo.Coin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * @ClassName CoinImpl
 * @Description 货币实现类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@Service
public class CoinImpl implements ICoinService {

    @Resource
    private CoinMapper coinMapper;

    public Coin get(int id) {
        return coinMapper.findById(id);
    }

    public boolean add(Coin coin) {
        return coinMapper.create(coin);
    }

    public List<Coin> list() {
        return coinMapper.findAll();
    }
}
