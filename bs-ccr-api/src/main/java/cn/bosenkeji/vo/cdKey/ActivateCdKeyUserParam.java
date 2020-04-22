package cn.bosenkeji.vo.cdKey;

public class ActivateCdKeyUserParam {

    private int userId;
    private String cdKey;

    public ActivateCdKeyUserParam() {
    }

    public ActivateCdKeyUserParam(int userId, String cdKey) {
        this.userId = userId;
        this.cdKey = cdKey;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCdKey() {
        return cdKey;
    }

    public void setCdKey(String cdKey) {
        this.cdKey = cdKey;
    }
}
