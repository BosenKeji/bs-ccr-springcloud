package cn.bosenkeji.vo.cdKey;

public class RenewCdKeyUserParam{

    private int userId;
    private int userProductComboId;
    private String cdKey;

    public RenewCdKeyUserParam() { }

    public RenewCdKeyUserParam(int userId, int userProductComboId, String cdKey) {
        this.userId = userId;

        this.userProductComboId = userProductComboId;
        this.cdKey = cdKey;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserProductComboId() {
        return userProductComboId;
    }

    public void setUserProductComboId(int userProductComboId) {
        this.userProductComboId = userProductComboId;
    }

    public String getCdKey() {
        return cdKey;
    }

    public void setCdKey(String cdKey) {
        this.cdKey = cdKey;
    }
}
