package cn.bosenkeji.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author xivinChen
 */
public class UserProductComboDay implements Serializable {
    private int id;

    private int userId;

    private int userProductComboId;

    private int type;

    private int number;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public UserProductComboDay(int id, int userId, int userProductComboId, int type, int number, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.userProductComboId = userProductComboId;
        this.type = type;
        this.number = number;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserProductComboDay() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserProductComboId() {
        return userProductComboId;
    }

    public void setUserProductComboId(int userProductComboId) {
        this.userProductComboId = userProductComboId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
        return "UserProductComboDay{" +
                "id=" + id +
                ", userId=" + userId +
                ", userProductComboId=" + userProductComboId +
                ", type=" + type +
                ", number=" + number +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}