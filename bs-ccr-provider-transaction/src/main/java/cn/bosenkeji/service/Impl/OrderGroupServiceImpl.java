package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.OrderGroupMapper;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.OrderGroupService;
import cn.bosenkeji.service.TradeOrderService;
import cn.bosenkeji.util.CommonConstantUtil;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.transaction.OrderGroup;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author CAJR
 * @date 2019/11/4 3:23 下午
 */
@Service
public class OrderGroupServiceImpl implements OrderGroupService {

    @Resource
    OrderGroupMapper orderGroupMapper;

    @Resource
    ICoinPairClientService iCoinPairClientService;

    @Resource
    TradeOrderService tradeOrderService;

    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int coinPairChoiceId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(this.orderGroupMapper.findAll(coinPairChoiceId));
    }

    @Override
    public OrderGroup getOneById(int orderGroupId) {
        OrderGroup orderGroup = this.orderGroupMapper.selectByPrimaryKey(orderGroupId);
        if (orderGroup != null){
            double endProfitRatio = orderGroup.getEndProfitRatio() / CommonConstantUtil.ACCURACY;

            if (orderGroup.getCoinPairChoice() != null){
                CoinPair coinPair = this.iCoinPairClientService.getCoinPair(orderGroup.getCoinPairChoice().getCoinPartnerId());
                orderGroup.getCoinPairChoice().setCoinPair(coinPair);
            }

            orderGroup.setEndProfitRatio(endProfitRatio);
            orderGroup.setTradeOrders(this.tradeOrderService.listByOrderGroupId(orderGroupId));
        }
        return orderGroup;
    }

    @Override
    public Optional<Integer> add(OrderGroup orderGroup) {
        double endProfitRatio = orderGroup.getEndProfitRatio() * CommonConstantUtil.ACCURACY;

        orderGroup.setEndProfitRatio(endProfitRatio);
        orderGroup.setStatus(CommonConstantUtil.ACTIVATE_STATUS);
        this.orderGroupMapper.insertSelective(orderGroup);
        return Optional.of(orderGroup.getId());
    }

    @Override
    public Optional<Integer> update(OrderGroup orderGroup) {
        return Optional.of(this.orderGroupMapper.updateByPrimaryKeySelective(orderGroup));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(this.orderGroupMapper.updateStatusByPrimaryKey(id,CommonConstantUtil.DELETE_STATUS, Timestamp.valueOf(LocalDateTime.now())));
    }

    @Override
    public Optional<Integer> checkExistByCoinPairChoiceIdAndIsEnd(int coinPairChoiceId) {
        return Optional.of(this.orderGroupMapper.checkExistByCoinPairChoiceIdAndIsEnd(coinPairChoiceId));
    }
}
