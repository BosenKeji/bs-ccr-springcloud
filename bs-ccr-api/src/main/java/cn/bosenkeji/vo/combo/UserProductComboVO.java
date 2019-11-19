package cn.bosenkeji.vo.combo;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.sql.Timestamp;

public class UserProductComboVO implements Serializable {

    private int id;

    private int productId;
    private String productName;
    private String productVersionName;

    private int productComboId;
    private String productComboName;

    private Timestamp createdAt;
    private int remainTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVersionName() {
        return productVersionName;
    }

    public void setProductVersionName(String productVersionName) {
        this.productVersionName = productVersionName;
    }

    public int getProductComboId() {
        return productComboId;
    }

    public void setProductComboId(int productComboId) {
        this.productComboId = productComboId;
    }

    public String getProductComboName() {
        return productComboName;
    }

    public void setProductComboName(String productComboName) {
        this.productComboName = productComboName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }
}
