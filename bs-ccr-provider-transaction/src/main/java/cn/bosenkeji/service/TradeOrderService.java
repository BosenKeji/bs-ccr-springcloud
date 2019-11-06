package cn.bosenkeji.service;

import cn.bosenkeji.OpenSearchPage;
import cn.bosenkeji.vo.transaction.OrderSearchRequestVo;
import cn.bosenkeji.vo.transaction.TradeOrder;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author CAJR
 * @date 2019/11/1 5:50 下午
 */
public interface TradeOrderService {

    PageInfo listByPage(int pageNum, int pageSize);

    List<TradeOrder> listByOrderGroupId(int orderGroupId);

    TradeOrder getById(int tradeOrderId);

    Optional<Integer> add(TradeOrder tradeOrder);

    Optional<Integer> update(TradeOrder tradeOrder);

    Optional<Integer> delete(int id);

    OpenSearchPage searchTradeOrderByCondition(OrderSearchRequestVo orderSearchRequestVo, int pageNum, int pageSize);

    Integer updateOpenSearchFromSql(int id);
}
