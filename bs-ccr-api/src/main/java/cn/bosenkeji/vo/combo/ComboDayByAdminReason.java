package cn.bosenkeji.vo.combo;

import cn.bosenkeji.vo.reason.Reason;

import java.io.Serializable;
import java.sql.Timestamp;

public class ComboDayByAdminReason implements Serializable {
    private int id;

    private int userProductComboDayByAdminId;

    private int reasonId;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Reason reason;

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserProductComboDayByAdminId() {
        return userProductComboDayByAdminId;
    }

    public void setUserProductComboDayByAdminId(int userProductComboDayByAdminId) {
        this.userProductComboDayByAdminId = userProductComboDayByAdminId;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
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