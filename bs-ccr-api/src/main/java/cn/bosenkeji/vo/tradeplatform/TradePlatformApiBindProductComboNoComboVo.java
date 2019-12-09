package cn.bosenkeji.vo.tradeplatform;

import java.io.Serializable;

public class TradePlatformApiBindProductComboNoComboVo implements Serializable {

    private String sign;
    private int type;
    private int apiBindRobotId;
    private int tradePlatformApiId;
    private int tradePlatformId;
    private int userId;


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getApiBindRobotId() {
        return apiBindRobotId;
    }

    public void setApiBindRobotId(int apiBindRobotId) {
        this.apiBindRobotId = apiBindRobotId;
    }

    public int getTradePlatformApiId() {
        return tradePlatformApiId;
    }

    public void setTradePlatformApiId(int tradePlatformApiId) {
        this.tradePlatformApiId = tradePlatformApiId;
    }

    public int getTradePlatformId() {
        return tradePlatformId;
    }

    public void setTradePlatformId(int tradePlatformId) {
        this.tradePlatformId = tradePlatformId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
