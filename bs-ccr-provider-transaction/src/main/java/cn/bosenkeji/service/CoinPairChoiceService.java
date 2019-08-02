package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairChoice;
import cn.bosenkeji.vo.transaction.CoinPairChoiceJoinCoinPair;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/17 11:48
 */
public interface CoinPairChoiceService {
    List<CoinPairChoice> list(int userId,int coinId);

    PageInfo listByPage(int pageNum,int pageSize,int userId,int coinId);

    Optional<CoinPairChoice> get(int id);

    Optional<Integer> add(CoinPairChoice coinPairChoice);

    Optional<Integer> update(CoinPairChoice coinPairChoice);

    Optional<Integer> delete(int id);

    Optional<Integer> checkExistByCoinPartnerIdAndUserId(int coinPartnerId,int userId);

    List<CoinPairChoiceJoinCoinPair> listCoinPairChoice();
}