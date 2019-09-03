package cn.bosenkeji.vo;


import com.alibaba.fastjson.JSONObject;

/**
 *
 * java需要修改redis 的数据
 *
 */

public class RedisParameter {

    private String redisKey;
    private JSONObject jsonObject;

    private Double historyMaxBenefitRatio; //历史最高收益比
    private Integer isTriggerTraceStopProfit; //是否触发追踪止盈

    private Integer isFollowBuild; //是否触发追踪建仓
    private Double minAveragePrice; //最小交易均价
    private Double realTimeEarningRatio;  //实时收益比

    public RedisParameter() { }

    public RedisParameter(String redisKey, JSONObject jsonObject, Double historyMaxBenefitRatio,
                         Integer isTriggerTraceStopProfit, Integer isFollowBuild, Double minAveragePrice) {
        this.redisKey = redisKey;
        this.jsonObject = jsonObject;
        this.historyMaxBenefitRatio = historyMaxBenefitRatio;
        this.isTriggerTraceStopProfit = isTriggerTraceStopProfit;
        this.isFollowBuild = isFollowBuild;
        this.minAveragePrice = minAveragePrice;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String rediskey) {
        this.redisKey = rediskey;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
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
}
