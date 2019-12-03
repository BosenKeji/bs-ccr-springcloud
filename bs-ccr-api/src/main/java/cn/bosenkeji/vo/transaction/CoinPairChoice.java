package cn.bosenkeji.vo.transaction;

import cn.bosenkeji.vo.coin.CoinPair;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/17 10:09
 */

//@JsonIgnoreProperties(value = {"handler"})  //初步判断对 缓存时序列化有影响
public class CoinPairChoice implements Serializable {
    @ApiModelProperty("自选币 id")
    private int id;

    @ApiModelProperty("货币对 id")
    private int coinPartnerId;

    @ApiModelProperty(value = "用户id",hidden = true)
    private int userId;

    @ApiModelProperty("机器人id")
    private int tradePlatformApiBindProductComboId;

    @ApiModelProperty("是否开始策略")
    private int isStart;

    @ApiModelProperty(hidden = true)
    private int status;

    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;

    /**非数据库字段*/
    private CoinPair coinPair;

    private CoinPairChoiceAttribute coinPairChoiceAttribute;

    private CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom;

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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CoinPairChoiceAttribute getCoinPairChoiceAttribute() {
        return coinPairChoiceAttribute;
    }

    public void setCoinPairChoiceAttribute(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        this.coinPairChoiceAttribute = coinPairChoiceAttribute;
    }

    public CoinPairChoiceAttributeCustom getCoinPairChoiceAttributeCustom() {
        return coinPairChoiceAttributeCustom;
    }

    public void setCoinPairChoiceAttributeCustom(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
        this.coinPairChoiceAttributeCustom = coinPairChoiceAttributeCustom;
    }

    public CoinPair getCoinPair() {
        return coinPair;
    }

    public void setCoinPair(CoinPair coinPair) {
        this.coinPair = coinPair;
    }

    public int getTradePlatformApiBindProductComboId() {
        return tradePlatformApiBindProductComboId;
    }

    public void setTradePlatformApiBindProductComboId(int tradePlatformApiBindProductComboId) {
        this.tradePlatformApiBindProductComboId = tradePlatformApiBindProductComboId;
    }
}