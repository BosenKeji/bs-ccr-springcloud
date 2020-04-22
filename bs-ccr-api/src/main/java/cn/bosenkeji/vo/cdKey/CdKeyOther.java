package cn.bosenkeji.vo.cdKey;

public class CdKeyOther {

    private int id;
    private String key;
    private String createAt;

    private int productComboId;
    private String productName;
    private String comboName;

    private int time;
    private String prefix;
    private String remark;

    private int isUsed;
    private String username;

    private int userProductComboId;
    private int robotManageStatus;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getUserProductComboId() {
        return userProductComboId;
    }

    public void setUserProductComboId(int userProductComboId) {
        this.userProductComboId = userProductComboId;
    }

    public int getRobotManageStatus() {
        return robotManageStatus;
    }

    public void setRobotManageStatus(int robotManageStatus) {
        this.robotManageStatus = robotManageStatus;
    }

    public CdKeyOther() { }

    public CdKeyOther(int id, String key, String createAt, int productComboId, String productName, String comboName, int time, String prefix, String remark, int isUsed, String username) {
        this.id = id;
        this.key = key;
        this.createAt = createAt;
        this.productComboId = productComboId;
        this.productName = productName;
        this.comboName = comboName;
        this.time = time;
        this.prefix = prefix;
        this.remark = remark;
        this.isUsed = isUsed;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getProductComboId() {
        return productComboId;
    }

    public void setProductComboId(int productComboId) {
        this.productComboId = productComboId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getProfix() {
        return prefix;
    }

    public void setProfix(String profix) {
        this.prefix = profix;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
