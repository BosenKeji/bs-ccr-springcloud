package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.OrderGroup;
import cn.bosenkeji.vo.transaction.OrderGroupOpenSearchFormat;
import cn.bosenkeji.vo.transaction.OrderGroupOther;
import cn.bosenkeji.vo.transaction.OrderGroupOverviewResult;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author CAJR
 * @date 2019/11/1 5:49 下午
 */
public interface OrderGroupService {

    PageInfo listByPage(int pageNum, int pageSize,int coinPairChoiceId);

    Optional<Integer> checkExistByCoinPairChoiceIdAndIsEnd(int coinPairChoiceId,int isEnd);

    OrderGroup getOneById(int orderGroupId);

    Optional<Integer> add(OrderGroup orderGroup);

    Optional<Integer> update(OrderGroup orderGroup);

    Optional<Integer> delete(int id);

    Optional<Integer> checkExistByCoinPairChoiceIdAndIsEnd(int coinPairChoiceId);

    Optional<Integer> resultNotEndGroupId(int coinPairChoiceId);

    Optional<Integer> checkExistByID(int orderGroupId);

    Optional<Integer> checkExistByGroupName(String name);

    List<OrderGroupOpenSearchFormat> searchTradeRecordByCondition(Long startTime, Long endTime, int coinPairChoiceId);

    List<OrderGroup> partFindByCoinPairChoiceIds(List<Integer> coinPairChoiceIds);

    Integer updateOpenSearchFromSql(int id);

    OrderGroupOverviewResult tradeOverview(int coinPairChoiceId);

    OrderGroup getByCoinPairChoiceId (int coinPairChoiceId);

    int getGroupIdByName(String name);
}
