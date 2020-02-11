package cn.bosenkeji.vo.combo;

import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;


import java.io.Serializable;
import java.sql.Timestamp;

public class UserProductComboVO implements Serializable {

    private int id;

    private int productId;
    private String productName;
    private String productVersionName;
    private String productLogo;

    private int productComboId;
    private String productComboName;

    private Timestamp createdAt;
    private int remainTime;
    private int runStatus;

    private int userId;
    private String tel;

    public String getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(String productLogo) {
        this.productLogo = productLogo;
    }

    private TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo;

    public TradePlatformApiBindProductCombo getTradePlatformApiBindProductCombo() {
        return tradePlatformApiBindProductCombo;
    }

    public void setTradePlatformApiBindProductCombo(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
        this.tradePlatformApiBindProductCombo = tradePlatformApiBindProductCombo;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }
}
