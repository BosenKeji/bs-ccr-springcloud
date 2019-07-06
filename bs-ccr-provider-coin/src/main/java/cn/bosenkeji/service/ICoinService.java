package cn.bosenkeji.service;

import cn.bosenkeji.vo.Coin;

import java.util.List;

public interface ICoinService {
    Coin get(int id);
    boolean add(Coin coin);
    List<Coin> list();
}
