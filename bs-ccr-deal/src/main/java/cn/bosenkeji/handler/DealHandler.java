package cn.bosenkeji.handler;


public class DealHandler {


    /**
     * 计算实时收益比
     * @param number 持仓数量
     * @param cost 持仓费用
     * @param realTimePrice 实时买价
     * @return 实时收益比
     */
    private double countRealTimeEarningRatio(double number, double cost, double realTimePrice) {
        return realTimePrice*number/cost;
    }


    /**
     * 确定买的逻辑
     */

    public boolean isBuy(double realTimePrice, int orderNumber, int maxOrderNumber,
                         double averagePosition, double buildPositionInterval,double averagePrice) {
        //是否需要判断？ 达到最大交易单数？ 用户已暂停或停止？ Redis获取
        if ( orderNumber == maxOrderNumber ) {
            return false;
        }

        //是否为第一单？ 第一单直接购买
        if ( orderNumber == 0 ) {
            return true;
        }

        //TODO 计算持仓均价 参数传入 Redis取
        // 读取建仓间隔 Redis取
        // 读取交易速率 用于计算最大交易次数，最大交易次数从Redis里面取
        // 读取已做单数 Redis取

        //现价是否小于等于整体持仓均价-建仓间隔*N？
        if ( realTimePrice > (averagePosition-buildPositionInterval) ) {
            return false;
        }
        //TODO 读取下一单买入数量 Redis获取 暂时MOCK @hjh 2019.7.26
        double nextOrderQuantity = 0;

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


    public boolean isSell(double positionPrice,int stopProfitType) {

        //TODO 读取持仓费用 参数传入 Redis获取

        //读取止盈金额和止盈比例，两种止盈方式，达到一种即可 参数传入

        //判断是否启用追踪止盈 if-else  1为追踪止盈，2为固定止盈
        boolean isStopProfitTrace = (stopProfitType==1) ? true : false;

        if (isStopProfitTrace) {
            //追踪止盈逻辑
            //持仓费用*（收益比-1）≥止盈金额？ 确定卖出

            //收益比≥1+触发比例？

            //记录实时收益比的最高数值

            //实时收益比≤最高实时收益比-回降比例？ 确定卖出

        } else {
            //固定止盈
            //持仓费用*（收益比-1）≥止盈金额？ //确定卖出

            //收益比≥1+止盈比例？ //确定卖出
        }


        return false;
    }


    /**
     * 计算拟买入均价
     * @param realTimePrice 实时价格
     * @return 拟买入均价
     */
    private double countAveragePrice(double realTimePrice) {

        return 0;
    }
}
