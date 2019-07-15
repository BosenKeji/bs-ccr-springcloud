package cn.bosenkeji.vo;

import java.io.Serializable;
import java.util.Date;

public class UserProductComboDayByAdmin implements Serializable {
    private Integer id;

    private Integer adminId;

    private Integer userProductComboDayId;

    private Byte status;

    private Date createdAt;

    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getUserProductComboDayId() {
        return userProductComboDayId;
    }

    public void setUserProductComboDayId(Integer userProductComboDayId) {
        this.userProductComboDayId = userProductComboDayId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}