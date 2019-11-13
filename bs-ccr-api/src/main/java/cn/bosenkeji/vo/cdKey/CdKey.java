package cn.bosenkeji.vo.cdKey;

import java.sql.Timestamp;
import java.util.Date;

public class CdKey {
    private int id;

    private String key;

    private int productComboId;

    private String username;

    private String remark;

    private Integer status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public CdKey() { }

    public CdKey(String key, int productComboId, String username, String remark, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.key = key;
        this.productComboId = productComboId;
        this.username = username;
        this.remark = remark;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CdKey(int id, String key, int productComboId, String username, String remark, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.key = key;
        this.productComboId = productComboId;
        this.username = username;
        this.remark = remark;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getProductComboId() {
        return productComboId;
    }

    public void setProductComboId(int productComboId) {
        this.productComboId = productComboId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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