package cn.bosenkeji.service;

import cn.bosenkeji.vo.CoinPairChoic;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/17 11:48
 */
public interface CoinPairChoicService {
    List<CoinPairChoic> list();

    PageInfo listByPage(int pageNum,int pageSize);

    Optional<CoinPairChoic> get(int id);

    boolean add(CoinPairChoic coinPairChoic);

    boolean update(CoinPairChoic coinPairChoic);

    boolean delete(int id);
}
