package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradeOrderMapper;
import cn.bosenkeji.service.TradeOrderService;
import cn.bosenkeji.util.CommonConstantUtil;
import cn.bosenkeji.vo.transaction.TradeOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author CAJR
 * @date 2019/11/4 3:24 下午
 */
@Service
public class TradeOrderServiceImpl implements TradeOrderService {

    @Resource
    TradeOrderMapper tradeOrderMapper;

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TradeOrder> tradeOrders = this.tradeOrderMapper.findAll();
        if (!CollectionUtils.isEmpty(tradeOrders)){
            tradeOrders.forEach(this::reverseTransformNumericalValue);
        }
        return new PageInfo<>(tradeOrders);
    }

    @Override
    public List<TradeOrder> listByOrderGroupId(int orderGroupId) {
        List<TradeOrder> tradeOrders = this.tradeOrderMapper.findAllByOrderGroupId(orderGroupId);
        if (!CollectionUtils.isEmpty(tradeOrders)){
            tradeOrders.forEach(this::reverseTransformNumericalValue);
        }
        return tradeOrders;
    }

    @Override
    public TradeOrder getById(int tradeOrderId) {
        TradeOrder tradeOrderResult =  this.tradeOrderMapper.selectByPrimaryKey(tradeOrderId);
        reverseTransformNumericalValue(tradeOrderResult);
        return tradeOrderResult;
    }

    private void transformNumericalValue(TradeOrder tradeOrder){
        double tradeAveragePrice = tradeOrder.getTradeAveragePrice()* CommonConstantUtil.ACCURACY;
        double tradeNumbers = tradeOrder.getTradeNumbers()* CommonConstantUtil.ACCURACY;
        double tradeCost = tradeOrder.getTradeCost()* CommonConstantUtil.ACCURACY;
        double shellProfit = tradeOrder.getShellProfit()* CommonConstantUtil.ACCURACY;

        tradeOrder.setStatus(CommonConstantUtil.ACTIVATE_STATUS);
        tradeOrder.setTradeAveragePrice(tradeAveragePrice);
        tradeOrder.setTradeNumbers(tradeNumbers);
        tradeOrder.setTradeCost(tradeCost);
        tradeOrder.setShellProfit(shellProfit);
        tradeOrder.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }


    private void reverseTransformNumericalValue(TradeOrder tradeOrder){
        double tradeAveragePrice = tradeOrder.getTradeAveragePrice() / CommonConstantUtil.ACCURACY;
        double tradeNumbers = tradeOrder.getTradeNumbers() / CommonConstantUtil.ACCURACY;
        double tradeCost = tradeOrder.getTradeCost() / CommonConstantUtil.ACCURACY;
        double shellProfit = tradeOrder.getShellProfit() / CommonConstantUtil.ACCURACY;


        tradeOrder.setTradeAveragePrice(tradeAveragePrice);
        tradeOrder.setTradeNumbers(tradeNumbers);
        tradeOrder.setTradeCost(tradeCost);
        tradeOrder.setShellProfit(shellProfit);
    }

    @Override
    public Optional<Integer> add(TradeOrder tradeOrder) {
        transformNumericalValue(tradeOrder);
        return Optional.of(this.tradeOrderMapper.insertSelective(tradeOrder));
    }

    @Override
    public Optional<Integer> update(TradeOrder tradeOrder) {
        return Optional.of(this.tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(this.tradeOrderMapper.deleteByPrimaryKey(id));
    }
}
