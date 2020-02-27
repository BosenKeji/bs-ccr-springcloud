package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.CoinPairChoice;
import cn.bosenkeji.vo.transaction.CoinPairChoiceJoinCoinPair;
import cn.bosenkeji.vo.transaction.CoinPairChoicePositionDetailResult;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @Author CAJR
 * @create 2019/7/17 11:48
 */
public interface CoinPairChoiceService {

    PageInfo listByPage(int pageNum, int pageSize, int tradePlatformApiBindProductComboId,int coinId);

    PageInfo recordByPage(int pageNum, int pageSize, int tradePlatformApiBindProductComboId, int coinId, String type);

    CoinPairChoice get(int id);

    CoinPairChoice getByDisregardStatus(int id);

    Optional<Integer> add(CoinPairChoice coinPairChoice);

    Optional<Integer> update(CoinPairChoice coinPairChoice);

    Optional<Integer> delete(int id);

    Optional<Integer> checkExistByCoinPartnerNameAndRobotId(String coinPairName, int tradePlatformApiBindProductComboId);

    Optional<Integer> checkExistByCoinPartnerIdAndRobotId(int coinPairId,int tradePlatformApiBindProductComboId);

    List<CoinPairChoice> findAll();

    Optional<Integer> batchDelete(String idStr,int userId);

    List<Integer> findAllCoinPartnerChoiceId();

    CoinPairChoicePositionDetailResult getCoinPairChoicePositionDetail(int coinPairChoiceId);

    List<CoinPairChoice> findByTradePlatformApiBindProductComboIdsAndStatus(Set<Integer> tradePlatformApiBindProductComboIds);
}
