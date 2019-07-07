package cn.bosenkeji.mapper;

import java.util.List;
import cn.bosenkeji.vo.Coin;
/**
 * @ClassName CoinMapper
 * @Description 货币
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
public interface CoinMapper {
    boolean create(Coin coin);
    public Coin findById(int id);
    public List<Coin> findAll();
}