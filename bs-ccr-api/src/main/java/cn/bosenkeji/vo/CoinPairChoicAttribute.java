package cn.bosenkeji.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/17 10:10
 */

public class CoinPairChoicAttribute {
    private int id;

    private int coinPartnerChoicId;

    private int expectMoney;

    private int strategyId;

    private int isCustom;

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

    public int getExpectMoney() {
        return expectMoney;
    }

    public void setExpectMoney(int expectMoney) {
        this.expectMoney = expectMoney;
    }

    public int getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(int strategyId) {
        this.strategyId = strategyId;
    }

    public int getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(int isCustom) {
        this.isCustom = isCustom;
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