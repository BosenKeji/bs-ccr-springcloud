package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.OrderGroupMapper;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.OrderGroupService;
import cn.bosenkeji.service.TradeOrderService;
import cn.bosenkeji.util.CommonConstantUtil;
import cn.bosenkeji.vo.OpenSearchFormat;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.transaction.OrderGroup;
import com.alibaba.fastjson.JSON;
import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private DocumentClient documentClient;

    @Resource
    TradeOrderService tradeOrderService;

    private static String appName = "bs_ccr_trade_dev";
    private static String orderGroupTable="order_group";

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
        pushToOpenSearch(orderGroup.getId());
        return Optional.of(orderGroup.getId());
    }

    public boolean pushToOpenSearch(int orderGroupId) {

        OrderGroup orderGroup = getOneById(orderGroupId);
        orderGroup.setTradeOrders(null);
        orderGroup.setCoinPairChoice(null);
        OpenSearchFormat<OrderGroup> format=new OpenSearchFormat();

        format.setFields(orderGroup);
        format.setCmd("ADD");

        List<OpenSearchFormat> list=new ArrayList<>();
        list.add(format);

        String jsonList = JSON.toJSONString(list);
        System.out.println("jsonList = " + jsonList);



        try {
            OpenSearchResult pushResult = documentClient.push(jsonList, appName, orderGroupTable);
            if(pushResult.getResult().equalsIgnoreCase("true")) {
                System.out.println("pushResult = " + pushResult+"推送成功");
                return true;
            }else {
                System.out.println("push 失败 "+pushResult.getTraceInfo());
                return false;
            }

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
