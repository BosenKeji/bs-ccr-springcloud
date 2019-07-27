package cn.bosenkeji.vo.tradeplatform;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author CAJR
 * @create 2019/7/9 16:27
 */

public class TradePlatformApi implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("交易平台api id")
    private int id;

    @ApiModelProperty("用户 id")
    private int userId;

    @ApiModelProperty("交易平台id")
    private int tradePlatformId;

    @ApiModelProperty("标识")
    private String sign;

    @ApiModelProperty("访问密钥")
    private String accessKey;

    @ApiModelProperty("加密后密钥")
    private String secretKey;

    @ApiModelProperty("别名")
    private String nickname;


    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    /**
     * @author xivinChen
     * 一对一属性
     */
    @ApiModelProperty(hidden = true)
    private TradePlatform tradePlatform;

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

    public int getTradePlatformId() {
        return tradePlatformId;
    }

    public void setTradePlatformId(int tradePlatformId) {
        this.tradePlatformId = tradePlatformId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public TradePlatform getTradePlatform() {
        return tradePlatform;
    }

    public void setTradePlatform(TradePlatform tradePlatform) {
        this.tradePlatform = tradePlatform;
    }

    @Override
    public String toString() {
        return "TradePlatformApi{" +
                "id=" + id +
                ", userId=" + userId +
                ", tradePlatformId=" + tradePlatformId +
                ", sign='" + sign + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", nickname='" + nickname + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", tradePlatform=" + tradePlatform +
                '}';
    }
}