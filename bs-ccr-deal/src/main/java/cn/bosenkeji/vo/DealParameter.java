package cn.bosenkeji.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * deal所需参数
 * @author hjh
 */

public class DealParameter {

    private String redisKey;
    private JSONObject jsonObject;

    private String accessKey;
    private String SecretKey;
    private String symbol;

    private Integer canSendMsgToNode; //是否给node端发送消息
    private Double positionCost; //持仓费用
    private Double positionNum; //持仓数量

    //买需要的参数
    private Integer finishedOrder; //已做单数
    private Integer maxTradeOrder; //最大交易单数
    private Double storeSplit; //建仓间隔

    private JSONObject buyVolume; //买入量
    private Double followLowerRatio; //追踪下调比
    private Double followCallbackRatio; //追踪回调比

    private Double minAveragePrice; //最小交易均价
    private Double firstOrderPrice; //首单现价
    private Integer isFollowBuild; //是否触发追踪建仓

    //卖需要的参数
    private Double targetProfitPrice; //止盈金额
    private Integer isUseFollowTargetProfit;  //是否使用追踪止盈
    private Double turnDownRatio; //追踪止盈触发比例

    private Double emitRatio; //追踪止盈回调比例
    private Double historyMaxRiskBenefitRatio; //历史最高收益比
    private Integer isTriggerTraceStopProfit; //是否触发追踪止盈


    public DealParameter() { }

    public DealParameter(String accessKey, String secretKey, String symbol,
                         Integer canSendMsgToNode, Double positionCost, Double positionNum,
                         Integer finishedOrder, Integer maxTradeOrder, Double storeSplit,
                         JSONObject buyVolume, Double followLowerRatio, Double followCallbackRatio,
                         Double minAveragePrice, Double firstOrderPrice, Integer isFollowBuild,
                         Double targetProfitPrice, Integer isUseFollowTargetProfit, Double turnDownRatio,
                         Double emitRatio, String redisKey, JSONObject jsonObject, Double historyMaxRiskBenefitRatio,
                         Integer isTriggerTraceStopProfit) {
        this.accessKey = accessKey;
        SecretKey = secretKey;
        this.symbol = symbol;
        this.canSendMsgToNode = canSendMsgToNode;
        this.positionCost = positionCost;
        this.positionNum = positionNum;
        this.finishedOrder = finishedOrder;
        this.maxTradeOrder = maxTradeOrder;
        this.storeSplit = storeSplit;
        this.buyVolume = buyVolume;
        this.followLowerRatio = followLowerRatio;
        this.followCallbackRatio = followCallbackRatio;
        this.minAveragePrice = minAveragePrice;
        this.firstOrderPrice = firstOrderPrice;
        this.isFollowBuild = isFollowBuild;
        this.targetProfitPrice = targetProfitPrice;
        this.isUseFollowTargetProfit = isUseFollowTargetProfit;
        this.turnDownRatio = turnDownRatio;
        this.emitRatio = emitRatio;
        this.redisKey = redisKey;
        this.jsonObject = jsonObject;
        this.historyMaxRiskBenefitRatio = historyMaxRiskBenefitRatio;
        this.isTriggerTraceStopProfit = isTriggerTraceStopProfit;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getCanSendMsgToNode() {
        return canSendMsgToNode;
    }

    public void setCanSendMsgToNode(Integer canSendMsgToNode) {
        this.canSendMsgToNode = canSendMsgToNode;
    }

    public Double getPositionCost() {
        return positionCost;
    }

    public void setPositionCost(Double positionCost) {
        this.positionCost = positionCost;
    }

    public Double getPositionNum() {
        return positionNum;
    }

    public void setPositionNum(Double positionNum) {
        this.positionNum = positionNum;
    }

    public Integer getFinishedOrder() {
        return finishedOrder;
    }

    public void setFinishedOrder(Integer finishedOrder) {
        this.finishedOrder = finishedOrder;
    }

    public Integer getMaxTradeOrder() {
        return maxTradeOrder;
    }

    public void setMaxTradeOrder(Integer maxTradeOrder) {
        this.maxTradeOrder = maxTradeOrder;
    }

    public Double getStoreSplit() {
        return storeSplit;
    }

    public void setStoreSplit(Double storeSplit) {
        this.storeSplit = storeSplit;
    }

    public JSONObject getBuyVolume() {
        return buyVolume;
    }

    public void setBuyVolume(JSONObject buyVolume) {
        this.buyVolume = buyVolume;
    }

    public Double getFollowLowerRatio() {
        return followLowerRatio;
    }

    public void setFollowLowerRatio(Double followLowerRatio) {
        this.followLowerRatio = followLowerRatio;
    }

    public Double getFollowCallbackRatio() {
        return followCallbackRatio;
    }

    public void setFollowCallbackRatio(Double followCallbackRatio) {
        this.followCallbackRatio = followCallbackRatio;
    }

    public Double getMinAveragePrice() {
        return minAveragePrice;
    }

    public void setMinAveragePrice(Double minAveragePrice) {
        this.minAveragePrice = minAveragePrice;
    }

    public Double getFirstOrderPrice() {
        return firstOrderPrice;
    }

    public void setFirstOrderPrice(Double firstOrderPrice) {
        this.firstOrderPrice = firstOrderPrice;
    }

    public Integer getIsFollowBuild() {
        return isFollowBuild;
    }

    public void setIsFollowBuild(Integer isFollowBuild) {
        this.isFollowBuild = isFollowBuild;
    }

    public Double getTargetProfitPrice() {
        return targetProfitPrice;
    }

    public void setTargetProfitPrice(Double targetProfitPrice) {
        this.targetProfitPrice = targetProfitPrice;
    }

    public Integer getIsUseFollowTargetProfit() {
        return isUseFollowTargetProfit;
    }

    public void setIsUseFollowTargetProfit(Integer isUseFollowTargetProfit) {
        this.isUseFollowTargetProfit = isUseFollowTargetProfit;
    }

    public Double getTurnDownRatio() {
        return turnDownRatio;
    }

    public void setTurnDownRatio(Double turnDownRatio) {
        this.turnDownRatio = turnDownRatio;
    }

    public Double getEmitRatio() {
        return emitRatio;
    }

    public void setEmitRatio(Double emitRatio) {
        this.emitRatio = emitRatio;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Double getHistoryMaxRiskBenefitRatio() {
        return historyMaxRiskBenefitRatio;
    }

    public void setHistoryMaxRiskBenefitRatio(Double historyMaxRiskBenefitRatio) {
        this.historyMaxRiskBenefitRatio = historyMaxRiskBenefitRatio;
    }

    public Integer getIsTriggerTraceStopProfit() {
        return isTriggerTraceStopProfit;
    }

    public void setIsTriggerTraceStopProfit(Integer isTriggerTraceStopProfit) {
        this.isTriggerTraceStopProfit = isTriggerTraceStopProfit;
    }
}
