package cn.bosenkeji.vo.coin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/9 16:27
 */

public class CoinSort implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("货币排序 id")
    private int id;

    @ApiModelProperty("交易平台 id")
    private int tradePlatformId
            ;
    @ApiModelProperty("货币 id")
    private int coinId;

    @ApiModelProperty("货币类型 1计价货币  2交易货币")
    private int type;

    @ApiModelProperty("排序序号")
    private int sortNum;

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

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
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
        return "CoinSort{" +
                "id=" + id +
                ", tradePlatformId=" + tradePlatformId +
                ", coinId=" + coinId +
                ", type=" + type +
                ", sortNum=" + sortNum +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}