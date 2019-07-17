package cn.bosenkeji.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/17 10:12
 */

public class CoinPairChoicAttributeCustom {
    private int id;

    private int coinPartnerChoicId;

    private int stopProfitType;

    private int stopProfitMoney;

    private Double stopProfitTraceTriggerRate;

    private Double stopProfitTraceDropRate;

    private Double stopProfitFixedRate;

    private int status;

    private Timestamp createdAt;

    private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinPartnerChoicId() {
        return coinPartnerChoicId;
    }

    public void setCoinPartnerChoicId(int coinPartnerChoicId) {
        this.coinPartnerChoicId = coinPartnerChoicId;
    }

    public int getStopProfitType() {
        return stopProfitType;
    }

    public void setStopProfitType(int stopProfitType) {
        this.stopProfitType = stopProfitType;
    }

    public int getStopProfitMoney() {
        return stopProfitMoney;
    }

    public void setStopProfitMoney(int stopProfitMoney) {
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}