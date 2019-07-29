package cn.bosenkeji.vo.tradeplatform;

import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

@JsonIgnoreProperties(value = {"handler"})
public class TradePlatformApiBindProductCombo implements Serializable {
    private int id;

    private int userId;

    private int tradePlatformApiId;

    private int userProductComboId;

    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;

    //一对一关系
    @ApiModelProperty(hidden = true)
    private TradePlatformApi tradePlatformApi;

    //一对一关系
    @ApiModelProperty(hidden = true)
    private UserProductCombo userProductCombo;

    public TradePlatformApiBindProductCombo() {
        super();
    }

    public TradePlatformApi getTradePlatformApi() {
        return tradePlatformApi;
    }

    public void setTradePlatformApi(TradePlatformApi tradePlatformApi) {
        this.tradePlatformApi = tradePlatformApi;
    }

    public UserProductCombo getUserProductCombo() {
        return userProductCombo;
    }

    public void setUserProductCombo(UserProductCombo userProductCombo) {
        this.userProductCombo = userProductCombo;
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

    public int getTradePlatformApiId() {
        return tradePlatformApiId;
    }

    public void setTradePlatformApiId(int tradePlatformApiId) {
        this.tradePlatformApiId = tradePlatformApiId;
    }

    public int getUserProductComboId() {
        return userProductComboId;
    }

    public void setUserProductComboId(int userProductComboId) {
        this.userProductComboId = userProductComboId;
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
        return "TradePlatformApiBindProductCombo{" +
                "id=" + id +
                ", userId=" + userId +
                ", tradePlatformApiId=" + tradePlatformApiId +
                ", userProductComboId=" + userProductComboId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", tradePlatformApi=" + tradePlatformApi +
                ", userProductCombo=" + userProductCombo +
                '}';
    }
}