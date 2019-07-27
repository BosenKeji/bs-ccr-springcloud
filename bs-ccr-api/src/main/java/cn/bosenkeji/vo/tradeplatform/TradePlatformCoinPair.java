package cn.bosenkeji.vo.tradeplatform;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * @Author CAJR
 * @create 2019/7/16 14:53
 */

public class TradePlatformCoinPair {
    @ApiModelProperty("交易平台货币对 id")
    private int id;
    @ApiModelProperty("交易平台id")
    private int tradePlatformId;

    @ApiModelProperty("货币对 id")
    private int coinPairId;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTradePlatformId() {
        return tradePlatformId;
    }

    public void setTradePlatformId(int tradePlatformId) {
        this.tradePlatformId = tradePlatformId;
    }

    public int getCoinPairId() {
        return coinPairId;
    }

    public void setCoinPairId(int coinPairId) {
        this.coinPairId = coinPairId;
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
}