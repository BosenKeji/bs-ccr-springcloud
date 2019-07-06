package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinMapper;
import cn.bosenkeji.service.ICoinService;
import cn.bosenkeji.vo.Coin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
