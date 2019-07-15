package cn.bosenkeji.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/15 17:27
 */

public class TradePlatformCoinPair {
    private int id;

    private int tradePlatformId;

    private int coinPairId;

    private int status;

    private Timestamp createdAt;

    private Date updatedAt;

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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}