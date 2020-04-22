package cn.bosenkeji.vo.cdKey;

import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.ProductCombo;
import cn.bosenkeji.vo.combo.UserProductCombo;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Date;

public class CdKey {
    private int id;

    private String key;

    private int productComboId;

    private String remark;

    private Integer status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Integer userId;
    private Integer userProductComboId;

    @ApiModelProperty(hidden = true)
    private User user;
    @ApiModelProperty(hidden = true)
    private ProductCombo productCombo;
    @ApiModelProperty(hidden = true)
    private UserProductCombo userProductCombo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductCombo getProductCombo() {
        return productCombo;
    }

    public void setProductCombo(ProductCombo productCombo) {
        this.productCombo = productCombo;
    }

    public UserProductCombo getUserProductCombo() {
        return userProductCombo;
    }

    public void setUserProductCombo(UserProductCombo userProductCombo) {
        this.userProductCombo = userProductCombo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserProductComboId() {
        return userProductComboId;
    }

    public void setUserProductComboId(Integer userProductComboId) {
        this.userProductComboId = userProductComboId;
    }

    public CdKey() { }

    public CdKey(String key, int productComboId, String remark, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.key = key;
        this.productComboId = productComboId;

        this.remark = remark;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CdKey(int id, String key, int productComboId, String remark, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.key = key;
        this.productComboId = productComboId;

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