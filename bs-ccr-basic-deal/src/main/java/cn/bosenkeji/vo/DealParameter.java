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

    /**
     *
     * tradeStatus：交易状态，用于判断是否需要走交易流程
     * positionCost：持仓费用
     * positionNum：持仓数量
     *
     * finishedOrder：已做单数
     * maxTradeOrder：最大交易单数
     * storeSplit：建仓间隔
     *
     * buyVolume：买入量
     * followLowerRatio：追踪下调比
     * followCallbackRatio：追踪回调比
     * firstOrderPrice：首单现价
     */
    private Integer tradeStatus;
    private Double positionCost;
    private Double positionNum;

    private Integer finishedOrder;
    private Integer maxTradeOrder;
    private Double storeSplit;

    private JSONObject buyVolume;
    private Double followLowerRatio;
    private Double followCallbackRatio;
    private Double firstOrderPrice;

    /**
     * targetProfitPrice：止盈金额
     * isStopProfitTrace：是否使用追踪止盈
     * turnDownRatio：追踪止盈触发比例
     * emitRatio：追踪止盈回调比例
     */

    private Double targetProfitPrice;
    private Integer isStopProfitTrace;
    private Double turnDownRatio;
    private Double emitRatio;

    private Double lastBuildPrice;

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

    public Double getLastBuildPrice() {
        return lastBuildPrice;
    }

    public void setLastBuildPrice(Double lastBuildPrice) {
        this.lastBuildPrice = lastBuildPrice;
    }

    @Override
    public String toString() {
        return "DealParameter{" +
                "trade=" + trade +
                ", symbol='" + symbol + '\'' +
                ", signId='" + signId + '\'' +
                ", tradeStatus=" + tradeStatus +
                ", positionCost=" + positionCost +
                ", positionNum=" + positionNum +
                ", finishedOrder=" + finishedOrder +
                ", maxTradeOrder=" + maxTradeOrder +
                ", storeSplit=" + storeSplit +
                ", buyVolume=" + buyVolume +
                ", followLowerRatio=" + followLowerRatio +
                ", followCallbackRatio=" + followCallbackRatio +
                ", firstOrderPrice=" + firstOrderPrice +
                ", targetProfitPrice=" + targetProfitPrice +
                ", isStopProfitTrace=" + isStopProfitTrace +
                ", turnDownRatio=" + turnDownRatio +
                ", emitRatio=" + emitRatio +
                ", lastBuildPrice=" + lastBuildPrice +
                '}';
    }
}
