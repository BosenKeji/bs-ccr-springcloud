package cn.bosenkeji.service;

import cn.bosenkeji.vo.Coin;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName CoinService
 * @Description 货币服务接口
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
public interface CoinService {
    List<Coin> list();

    Optional<Coin> get(int id);

    boolean add(Coin coin);

    boolean update(Coin coin);

    boolean delete(int id);

}
