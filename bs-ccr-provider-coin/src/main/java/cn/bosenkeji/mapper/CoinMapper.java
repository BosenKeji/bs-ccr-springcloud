package cn.bosenkeji.mapper;

import java.util.List;
import cn.bosenkeji.vo.Coin;

public interface CoinMapper {
    boolean create(Coin coin);
    public Coin findById(int id);
    public List<Coin> findAll();
}