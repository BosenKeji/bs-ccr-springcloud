package cn.bosenkeji.vo.transaction;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/17 10:12
 */

public class CoinPairChoiceAttributeCustom {
    @ApiModelProperty("自定义属性 id")
    private int id;

    @ApiModelProperty("货币对 id")
    private int coinPairChoiceId;

    @ApiModelProperty("止盈方式  1追踪止盈  2固定止盈")
    private int stopProfitType;

    @ApiModelProperty("止盈金额")
    private double stopProfitMoney;

    @ApiModelProperty("追踪止盈触发比例")
    private Double stopProfitTraceTriggerRate;

    @ApiModelProperty("追踪止盈回降比例")
    private Double stopProfitTraceDropRate;

    @ApiModelProperty("固定止盈比例")
    private Double stopProfitFixedRate;

    @ApiModelProperty("首次开仓价")
    private double firstOpenPrice;

    private int status;

    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinPairChoiceId() {
        return coinPairChoiceId;
    }

    public void setCoinPairChoiceId(int coinPairChoiceId) {
        this.coinPairChoiceId = coinPairChoiceId;
    }

    public int getStopProfitType() {
        return stopProfitType;
    }

    public void setStopProfitType(int stopProfitType) {
        this.stopProfitType = stopProfitType;
    }

    public double getStopProfitMoney() {
        return stopProfitMoney;
    }

    public void setStopProfitMoney(double stopProfitMoney) {
        this.stopProfitMoney = stopProfitMoney;
    }

    public Double getStopProfitTraceTriggerRate() {
        return stopProfitTraceTriggerRate;
    }

    public void setStopProfitTraceTriggerRate(Double stopProfitTraceTriggerRate) {
        this.stopProfitTraceTriggerRate = stopProfitTraceTriggerRate;
    }

    public Double getStopProfitTraceDropRate() {
        return stopProfitTraceDropRate;
    }

    public void setStopProfitTraceDropRate(Double stopProfitTraceDropRate) {
        this.stopProfitTraceDropRate = stopProfitTraceDropRate;
    }

    public Double getStopProfitFixedRate() {
        return stopProfitFixedRate;
    }

    public void setStopProfitFixedRate(Double stopProfitFixedRate) {
        this.stopProfitFixedRate = stopProfitFixedRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getFirstOpenPrice() {
        return firstOpenPrice;
    }

    public void setFirstOpenPrice(double firstOpenPrice) {
        this.firstOpenPrice = firstOpenPrice;
    }

    @Override
    public String toString() {
        return "CoinPairChoiceAttributeCustom{" +
                "id=" + id +
                ", coinPartnerChoiceId=" + coinPairChoiceId +
                ", stopProfitType=" + stopProfitType +
                ", stopProfitMoney=" + stopProfitMoney +
                ", stopProfitTraceTriggerRate=" + stopProfitTraceTriggerRate +
                ", stopProfitTraceDropRate=" + stopProfitTraceDropRate +
                ", stopProfitFixedRate=" + stopProfitFixedRate +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}