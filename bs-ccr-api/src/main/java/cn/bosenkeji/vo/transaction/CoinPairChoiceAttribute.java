package cn.bosenkeji.vo.transaction;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/17 10:10
 */

public class CoinPairChoiceAttribute {
    @ApiModelProperty("自选货币对属性 id")
    private int id;

    @ApiModelProperty("货币对 id")
    private int coinPartnerChoiceId;

    @ApiModelProperty("预算")
    private int expectMoney;

    @ApiModelProperty("策略 id")
    private int strategyId;

    @ApiModelProperty("是否自定义属性")
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

    public int getCoinPartnerChoiceId() {
        return coinPartnerChoiceId;
    }

    public void setCoinPartnerChoiceId(int coinPartnerChoiceId) {
        this.coinPartnerChoiceId = coinPartnerChoiceId;
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