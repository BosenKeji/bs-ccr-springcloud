package cn.bosenkeji.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/17 10:09
 */

public class CoinPairChoic {
    @ApiModelProperty("自选币 id")
    private int id;

    @ApiModelProperty("货币对 id")
    private int coinPartnerId;

    @ApiModelProperty("用户id")
    private int userId;

    @ApiModelProperty("是否开始策略")
    private int isStart;

    private int status;

    private Timestamp createdAt;

    private Date updatedAt;

    /*非数据库字段*/
    private CoinPairChoicAttribute coinPairChoicAttribute;

    private CoinPairChoicAttributeCustom coinPairChoicAttributeCustom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinPartnerId() {
        return coinPartnerId;
    }

    public void setCoinPartnerId(int coinPartnerId) {
        this.coinPartnerId = coinPartnerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsStart() {
        return isStart;
    }

    public void setIsStart(int isStart) {
        this.isStart = isStart;
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

    public CoinPairChoicAttribute getCoinPairChoicAttribute() {
        return coinPairChoicAttribute;
    }

    public void setCoinPairChoicAttribute(CoinPairChoicAttribute coinPairChoicAttribute) {
        this.coinPairChoicAttribute = coinPairChoicAttribute;
    }

    public CoinPairChoicAttributeCustom getCoinPairChoicAttributeCustom() {
        return coinPairChoicAttributeCustom;
    }

    public void setCoinPairChoicAttributeCustom(CoinPairChoicAttributeCustom coinPairChoicAttributeCustom) {
        this.coinPairChoicAttributeCustom = coinPairChoicAttributeCustom;
    }
}