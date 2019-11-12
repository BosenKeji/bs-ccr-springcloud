package cn.bosenkeji.vo.tradeplatform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CAJR
 * @date 2019/11/11 2:55 下午
 */
public class TradePlatformApiListResult {
    @ApiModelProperty("交易平台api id")
    private int id;

    @ApiModelProperty(value = "用户 id",hidden = true)
    private int userId;

    @ApiModelProperty("交易平台id")
    private int tradePlatformId;

    @ApiModelProperty("访问密钥")
    private String accessKey;

    @ApiModelProperty("别名")
    private String nickname;

    //add by xivin
    @ApiModelProperty(hidden = true)
    private int isBound;

    @JsonIgnore
    @ApiModelProperty("加密后密钥")
    private String secret;

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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public TradePlatform getTradePlatform() {
        return tradePlatform;
    }

    public void setTradePlatform(TradePlatform tradePlatform) {
        this.tradePlatform = tradePlatform;
    }

    public int getIsBound() {
        return isBound;
    }

    public void setIsBound(int isBound) {
        this.isBound = isBound;
    }
}
