package cn.bosenkeji.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author xivinChen
 */

public class ProductCombo implements Serializable {
    private int id;

    private Integer productId;

    private String name;

    private int time;

    private float price;

    private String remark;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    /*public ProductCombo(int id, Integer productId, String name, int time, float price, String remark, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.time = time;
        this.price = price;
        this.remark = remark;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }*/

    public ProductCombo() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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