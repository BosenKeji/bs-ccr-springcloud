package cn.bosenkeji.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * deal所需参数
 * @author hjh
 */

public class DealParameter {

    private Map<String,Object> trade;

    private String symbol;
    private String signId;

    private Integer tradeStatus; //是否给node端发送消息
    private Double positionCost; //持仓费用
    private Double positionNum; //持仓数量

    //买需要的参数
    private Integer finishedOrder; //已做单数
    private Integer maxTradeOrder; //最大交易单数
    private Double storeSplit; //建仓间隔

    private JSONObject buyVolume; //买入量
    private Double followLowerRatio; //追踪下调比
    private Double followCallbackRatio; //追踪回调比
    private Double firstOrderPrice; //首单现价

    //卖需要的参数
    private Double targetProfitPrice; //止盈金额
    private Integer isStopProfitTrace;  //是否使用追踪止盈
    private Double turnDownRatio; //追踪止盈触发比例
    private Double emitRatio; //追踪止盈回调比例

    public DealParameter() { }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
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

    public Double getFirstOrderPrice() {
        return firstOrderPrice;
    }

    public void setFirstOrderPrice(Double firstOrderPrice) {
        this.firstOrderPrice = firstOrderPrice;
    }

    public Double getTargetProfitPrice() {
        return targetProfitPrice;
    }

    public void setTargetProfitPrice(Double targetProfitPrice) {
        this.targetProfitPrice = targetProfitPrice;
    }

    public Integer getIsStopProfitTrace() {
        return isStopProfitTrace;
    }

    public void setIsStopProfitTrace(Integer isStopProfitTrace) {
        this.isStopProfitTrace = isStopProfitTrace;
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

    public Map<String, Object> getTrade() {
        return trade;
    }

    public void setTrade(Map<String, Object> trade) {
        this.trade = trade;
    }
}
