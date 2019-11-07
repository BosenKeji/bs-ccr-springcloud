package cn.bosenkeji.vo.transaction;

import java.io.Serializable;
import java.sql.Timestamp;

public class TradeOrderLogVo implements Serializable {

    private int id;
    private String coinPairName;
    private Timestamp createAt;
    private Double tradeNumbers;
    private Double tradeCost;
    private Double shellProfit;
    private Double endProfitRatio;

    private int endType;
    private int tradeType;
    private int coinPairId;
    private int coinPairChoiceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoinPairName() {
        return coinPairName;
    }

    public void setCoinPairName(String coinPairName) {
        this.coinPairName = coinPairName;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Double getTradeNumbers() {
        return tradeNumbers;
    }

    public void setTradeNumbers(Double tradeNumbers) {
        this.tradeNumbers = tradeNumbers;
    }

    public Double getTradeCost() {
        return tradeCost;
    }

    public void setTradeCost(Double tradeCost) {
        this.tradeCost = tradeCost;
    }

    public Double getShellProfit() {
        return shellProfit;
    }

    public void setShellProfit(Double shellProfit) {
        this.shellProfit = shellProfit;
    }

    public Double getEndProfitRatio() {
        return endProfitRatio;
    }

    public void setEndProfitRatio(Double endProfitRatio) {
        this.endProfitRatio = endProfitRatio;
    }

    public int getEndType() {
        return endType;
    }

    public void setEndType(int endType) {
        this.endType = endType;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public int getCoinPairId() {
        return coinPairId;
    }

    public void setCoinPairId(int coinPairId) {
        this.coinPairId = coinPairId;
    }

    public int getCoinPairChoiceId() {
        return coinPairChoiceId;
    }

    public void setCoinPairChoiceId(int coinPairChoiceId) {
        this.coinPairChoiceId = coinPairChoiceId;
    }
}
