package cn.bosenkeji.service;

import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:08
 */
public interface CoinPairService {
    List<CoinPair> list();

    PageInfo listByPage(int pageNum, int pageSize);

    Optional<CoinPair> get(int id);

    boolean add(CoinPair coinPair);

    boolean update(CoinPair coinPair);

    boolean delete(int id);
}
