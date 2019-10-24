package cn.bosenkeji.vo.combo;

import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author xivinChen
 */
@Api
public class UserProductComboDayByAdmin implements Serializable {
    private int id;

    private int adminId;

    private int userProductComboDayId;

    @ApiModelProperty(hidden = true)
    private int status;

    private String orderNumber;
    private String remark;
    private int comboDayReasonId;

    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;

    @ApiModelProperty(hidden = true)
    private Admin admin;

    //关联属性
    //@ApiModelProperty(hidden = true)
    private UserProductComboDay userProductComboDay;

    @ApiModelProperty(hidden = true)
    private ComboDayReason comboDayReason;

    public ComboDayReason getComboDayReason() {
        return comboDayReason;
    }

    public void setComboDayReason(ComboDayReason comboDayReason) {
        this.comboDayReason = comboDayReason;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getComboDayReasonId() {
        return comboDayReasonId;
    }

    public void setComboDayReasonId(int comboDayReasonId) {
        this.comboDayReasonId = comboDayReasonId;
    }

    public UserProductComboDay getUserProductComboDay() {
        return userProductComboDay;
    }

    public void setUserProductComboDay(UserProductComboDay userProductComboDay) {
        this.userProductComboDay = userProductComboDay;
    }

    public UserProductComboDayByAdmin(int id, int adminId, int userProductComboDayId, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.adminId = adminId;
        this.userProductComboDayId = userProductComboDayId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public UserProductComboDayByAdmin() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getUserProductComboDayId() {
        return userProductComboDayId;
    }

    public void setUserProductComboDayId(int userProductComboDayId) {
        this.userProductComboDayId = userProductComboDayId;
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
        return "UserProductComboDayByAdmin{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", userProductComboDayId=" + userProductComboDayId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", admin=" + admin +
                ", userProductComboDay=" + userProductComboDay +
                '}';
    }
}