package cn.bosenkeji.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class UserProductCombo implements Serializable {
    private int id;

    private int userId;

    private String orderNumber;

    private int productComboId;

    private String remark;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    //非数据库属性 剩余时长
    private int remainTime=0;


    public UserProductCombo(int id, int userId, String orderNumber, int productComboId, String remark, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.productComboId = productComboId;
        this.remark = remark;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public UserProductCombo() {
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getProductComboId() {
        return productComboId;
    }

    public void setProductComboId(int productComboId) {
        this.productComboId = productComboId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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