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

    boolean add(CoinPairChoice coinPairChoice);

    boolean update(CoinPairChoice coinPairChoice);

    boolean delete(int id);
}
