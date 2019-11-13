package cn.bosenkeji.vo.cdKey;

public class RenewCdKeyParam {

    private String cdKey;
    private int userProductComboId;

    public RenewCdKeyParam() { }

    public String getCdKey() {
        return cdKey;
    }

    public void setCdKey(String cdKey) {
        this.cdKey = cdKey;
    }

    public int getUserProductComboId() {
        return userProductComboId;
    }

    public void setUserProductComboId(int userProductComboId) {
        this.userProductComboId = userProductComboId;
    }
}
