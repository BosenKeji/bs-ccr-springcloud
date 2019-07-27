package cn.bosenkeji.handler;

import cn.bosenkeji.messaging.MySource;

import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.vo.strategy.Strategy;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



@RestController
public class DealHandler {

    @Autowired
    private MySource source;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IStrategyService strategyService;


    @RequestMapping("/strategy")
    public PageInfo testConsumer() {
        PageInfo<Strategy> strategyPageInfo = strategyService.listByPage(1, 10);
        return strategyPageInfo;
    }


    /**
     * 计算实时收益比  买价*持仓数量/持仓费用  精确小数点后4位
     * @param number 持仓数量
     * @param cost 持仓费用
     * @param realTimePrice 实时买价
     * @return 实时收益比
     */
    private double countRealTimeEarningRatio(double number, double cost, double realTimePrice) {
        return realTimePrice*number/cost;
    }

    /**
     * 计算拟买入均价
     * @param deep 实时深度
     * @param quantity 某交易量
     * @return 拟买入均价
     */
    private double countAveragePrice(Map<Integer,Double> deep,double quantity) {
        double priceSum = 0;
        double deepSum = 0;
        for (Map.Entry<Integer, Double> entry : deep.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            if ( quantity >= value) {
                priceSum += key*value;
                quantity -= value;
                deepSum += value;
            } else {
                priceSum += key*quantity;
                deepSum += quantity;
            }
        }
        return priceSum/deepSum;
    }


    /**
     * 是否买的逻辑
     * @param realTimePrice 实时价格 mq获取
     * @param orderNumber 已做单数 redis获取
     * @param maxOrderNumber 最大交易单数 redis获取
     * @param averagePosition 持仓均价 调用上面的公式
     * @param buildPositionInterval 建仓间隔 redis获取
     * @param averagePrice 拟买入均价 调用上面公式
     * @return 是否买
     */

    public boolean isBuy(double realTimePrice, int orderNumber, int maxOrderNumber,
                         double averagePosition, double buildPositionInterval,double averagePrice) {
        //是否需要判断？ 达到最大交易单数？
        if ( orderNumber == maxOrderNumber ) {
            return false;
        }
        //是否为第一单？ 第一单直接购买
        if ( orderNumber == 0 ) {
            return true;
        }
        //现价是否小于等于整体持仓均价-建仓间隔*N？
        if ( realTimePrice > (averagePosition-buildPositionInterval) ) {
            return false;
        }

        //计算拟买入均价 参数传入

        //TODO 追踪下调比 不知道是什么东西  暂时MOCK @hjh 2019.7.26
        // 获取下调均价 下调均价=(整体持仓均价-建仓间隔)-(整体持仓均价*追踪下调比)
        double followLowerRatio = 0.01;
        double lowerAveragePrice = (averagePosition - buildPositionInterval) - (averagePosition*followLowerRatio);

        //拟买入均价小于等于下调均价？ 触发追踪建仓
        if (averagePrice > lowerAveragePrice) {
            return false;
        }
        //TODO 最小均价 Redis取 暂时MOCK @hjh 2019.7.26
        // 追踪回调比 不知道是什么东西  暂时MOCK @hjh 2019.7.26
        //计算回调均价 回调均价=最小均价+整体持仓均价*追踪回调比
        double minAveragePrice = 1;
        double followCallbackRatio = 0.01;
        double callbackAveragePrice = minAveragePrice + averagePosition*followCallbackRatio;

        //拟买入均价是否大于等于回调均价？ 是则确定买入
        if ( averagePrice >= callbackAveragePrice ) {
            return true;
        }
        return false;
    }

    /**
     *  确定买的逻辑
     * @param positionPrice 持仓费用
     * @param stopProfitType 止盈方式
     * @param stopProfitPrice 止盈金额
     * @param stopProfitRatio 止盈比例
     * @param realTimeEarningRatio 实时收益比
     * @param triggerRatio 触发比例
     * @param callBackRatio 回调比例
     * @return 是否卖
     */

    public boolean isSell(double positionPrice, int stopProfitType, double stopProfitPrice
            ,double stopProfitRatio ,double realTimeEarningRatio,double triggerRatio, double callBackRatio) {
        //读取止盈金额和止盈比例，两种止盈方式，达到一种即可 参数传入
        //判断是否启用追踪止盈 if-else  1为追踪止盈，2为固定止盈
        boolean isStopProfitTrace = (stopProfitType==1) ? true : false;

        //持仓费用*（收益比-1）≥止盈金额？ 确定卖出
        if ( (positionPrice * (realTimeEarningRatio-1)) >= stopProfitPrice ) {
            return true;
        }
        if (isStopProfitTrace) {
            //追踪止盈逻辑

            //收益比≥1+触发比例？ 追踪止盈
            if (realTimeEarningRatio >= (1 + triggerRatio)) {
                //TODO 记录实时收益比的最高数值  抽想一个方法用于更改从redis获取的JSON字符串
                double maxEarningRation = 0;
                //实时收益比≤最高实时收益比-回降比例？ 确定卖出
                if (realTimeEarningRatio <= (maxEarningRation-callBackRatio)) {
                    return true;
                }
            }
        } else {
            //收益比≥1+止盈比例？ //确定卖出
            if (realTimeEarningRatio > (1 + stopProfitRatio)) {
                return true;
            }
        }
        return false;
    }



}