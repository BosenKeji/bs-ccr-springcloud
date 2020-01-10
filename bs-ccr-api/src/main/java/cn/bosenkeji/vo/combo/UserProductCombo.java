package cn.bosenkeji.vo.combo;

import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.ProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Api
@JsonIgnoreProperties(value = {"handler"})
@Validated
public class UserProductCombo implements Serializable {
    private int id;

    @Min(value = 1,message = "用户ID不能小于1")
    private int userId;

    private String orderNumber;

    @Min(value = 1,message = "套餐ID 不能小于1")
    private int productComboId;

    private String remark;

    @ApiModelProperty(hidden = true)
    private int status;

    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;

    //非数据库属性 剩余时长
    @ApiModelProperty(hidden = true)
    private int remainTime=0;

   // private int redisKeyId;

    //一对一
    @ApiModelProperty(hidden = true)
    private ProductCombo productCombo;

    @ApiModelProperty(hidden = true)
    private User user;

    /*@ApiModelProperty(hidden = true)
    private ComboRedisKey comboRedisKey;*/

    @ApiModelProperty(hidden = true)
    private List<UserProductComboDay> userProductComboDays;

    @ApiModelProperty(hidden = true)
    private TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TradePlatformApiBindProductCombo getTradePlatformApiBindProductCombo() {
        return tradePlatformApiBindProductCombo;
    }

    public void setTradePlatformApiBindProductCombo(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
        this.tradePlatformApiBindProductCombo = tradePlatformApiBindProductCombo;
    }

    public ProductCombo getProductCombo() {
        return productCombo;
    }

    public void setProductCombo(ProductCombo productCombo) {
        this.productCombo = productCombo;
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

    public List<UserProductComboDay> getUserProductComboDays() {
        return userProductComboDays;
    }

    public void setUserProductComboDays(List<UserProductComboDay> userProductComboDays) {
        this.userProductComboDays = userProductComboDays;
    }

    @Override
    public String toString() {
        return "UserProductCombo{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderNumber='" + orderNumber + '\'' +
                ", productComboId=" + productComboId +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", remainTime=" + remainTime +
                ", productCombo=" + productCombo +
                ", user=" + user +
                ", userProductComboDays=" + userProductComboDays +
                '}';
    }
}