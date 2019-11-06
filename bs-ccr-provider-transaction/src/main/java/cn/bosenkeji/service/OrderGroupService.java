package cn.bosenkeji.service;

import cn.bosenkeji.vo.transaction.OrderGroup;
import cn.bosenkeji.vo.transaction.OrderGroupOpenSearchFormat;
import cn.bosenkeji.vo.transaction.OrderGroupOther;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author CAJR
 * @date 2019/11/1 5:49 下午
 */
public interface OrderGroupService {

    PageInfo listByPage(int pageNum, int pageSize,int coinPairChoiceId);

    OrderGroup getOneById(int orderGroupId);

    Optional<Integer> add(OrderGroup orderGroup);

    Optional<Integer> update(OrderGroup orderGroup,int userId);

    Optional<Integer> delete(int id,int userId);

    Optional<Integer> checkExistByCoinPairChoiceIdAndIsEnd(int coinPairChoiceId);

    Optional<Integer> checkExistByID(int orderGroupId);

    List<OrderGroupOpenSearchFormat> searchTradeRecordByCondition(Long startTime, Long endTime, int coinPairChoiceId);
}
