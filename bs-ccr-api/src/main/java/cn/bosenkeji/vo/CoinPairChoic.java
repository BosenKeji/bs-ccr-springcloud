package cn.bosenkeji.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/17 10:09
 */

public class CoinPairChoic {
    private int id;

    private int coinPartnerId;

    private int userId;

    private int isStart;

    private int status;

    private Timestamp createdAt;

    private Date updatedAt;

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
}