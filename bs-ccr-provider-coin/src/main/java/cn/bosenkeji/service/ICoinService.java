package cn.bosenkeji.service;

import cn.bosenkeji.vo.Coin;

import java.util.List;

/**
 * @ClassName ICoinService
 * @Description 货币服务接口
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
public interface ICoinService {
    Coin get(int id);
    boolean add(Coin coin);
    List<Coin> list();
}
