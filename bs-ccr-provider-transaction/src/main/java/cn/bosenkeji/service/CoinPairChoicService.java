package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairChoice;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/17 11:48
 */
public interface CoinPairChoicService {
    List<CoinPairChoice> list();

    PageInfo listByPage(int pageNum,int pageSize);

    Optional<CoinPairChoice> get(int id);

    Optional<Integer> add(CoinPairChoice coinPairChoice);

    Optional<Integer> update(CoinPairChoice coinPairChoice);

    Optional<Integer> delete(int id);
}
