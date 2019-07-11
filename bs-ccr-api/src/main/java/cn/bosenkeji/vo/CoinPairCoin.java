package cn.bosenkeji.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/9 16:27
 */

public class CoinPairCoin implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int coinPairId;

    private int coinId;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinPairId() {
        return coinPairId;
    }

    public void setCoinPairId(int coinPairId) {
        this.coinPairId = coinPairId;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
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

    @Override
    public String toString() {
        return "CoinPairCoin{" +
                "id=" + id +
                ", coinPairId=" + coinPairId +
                ", coinId=" + coinId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}