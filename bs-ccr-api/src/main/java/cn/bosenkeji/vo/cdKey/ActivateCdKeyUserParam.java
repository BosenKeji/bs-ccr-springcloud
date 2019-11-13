package cn.bosenkeji.vo.cdKey;

public class ActivateCdKeyUserParam {

    private int userId;
    private String username;
    private String cdKey;

    public ActivateCdKeyUserParam() {
    }

    public ActivateCdKeyUserParam(int userId, String username, String cdKey) {
        this.userId = userId;
        this.username = username;
        this.cdKey = cdKey;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCdKey() {
        return cdKey;
    }

    public void setCdKey(String cdKey) {
        this.cdKey = cdKey;
    }
}
