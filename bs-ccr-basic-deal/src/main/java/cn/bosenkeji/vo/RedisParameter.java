package cn.bosenkeji.vo;

/**
 *
 * java需要修改redis 的数据
 *
 */

public class RedisParameter {

    private String redisKey;

    /**
     * historyMaxBenefitRatio：历史最高收益比
     * isTriggerTraceStopProfit：是否触发追踪止盈
     *
     * isFollowBuild：是否触发追踪建仓
     * minAveragePrice：最小交易均价
     * realTimeEarningRatio：实时收益比
     *
     * triggerStopProfitOrder：触发追踪止盈的单数
     * triggerFollowBuildOrder：触发追踪建仓的单数
     */

    private Double historyMaxBenefitRatio;
    private Integer isTriggerTraceStopProfit;

    private Integer isFollowBuild;
    private Double minAveragePrice;
    private Double realTimeEarningRatio;

    private Integer triggerStopProfitOrder;
    private Integer triggerFollowBuildOrder;

    public RedisParameter() { }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public Double getHistoryMaxBenefitRatio() {
        return historyMaxBenefitRatio;
    }

    public void setHistoryMaxBenefitRatio(Double historyMaxRiskBenefitRatio) {
        this.historyMaxBenefitRatio = historyMaxRiskBenefitRatio;
    }

    public Integer getIsTriggerTraceStopProfit() {
        return isTriggerTraceStopProfit;
    }

    public void setIsTriggerTraceStopProfit(Integer isTriggerTraceStopProfit) {
        this.isTriggerTraceStopProfit = isTriggerTraceStopProfit;
    }

    public Integer getIsFollowBuild() {
        return isFollowBuild;
    }

    public void setIsFollowBuild(Integer isFollowBuild) {
        this.isFollowBuild = isFollowBuild;
    }

    public Double getMinAveragePrice() {
        return minAveragePrice;
    }

    public void setMinAveragePrice(Double minAveragePrice) {
        this.minAveragePrice = minAveragePrice;
    }

    public Double getRealTimeEarningRatio() {
        return realTimeEarningRatio;
    }

    public void setRealTimeEarningRatio(Double realTimeEarningRatio) {
        this.realTimeEarningRatio = realTimeEarningRatio;
    }

    public Integer getTriggerStopProfitOrder() {
        return triggerStopProfitOrder;
    }

    public void setTriggerStopProfitOrder(Integer triggerStopProfitOrder) {
        this.triggerStopProfitOrder = triggerStopProfitOrder;
    }

    public Integer getTriggerFollowBuildOrder() {
        return triggerFollowBuildOrder;
    }

    public void setTriggerFollowBuildOrder(Integer triggerFollowBuildOrder) {
        this.triggerFollowBuildOrder = triggerFollowBuildOrder;
    }
}
