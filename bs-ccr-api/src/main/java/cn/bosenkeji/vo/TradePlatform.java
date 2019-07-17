package cn.bosenkeji.vo;

import java.sql.Timestamp;

/**
 * @Author CAJR
 * @create 2019/7/15 15:43
 */

public class TradePlatform {
    private int id;

    private String name;

    private String logo;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    /*非数据库字段*/
    private TradePlatformApi tradePlatformApi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public TradePlatformApi getTradePlatformApi() {
        return tradePlatformApi;
    }

    public void setTradePlatformApi(TradePlatformApi tradePlatformApi) {
        this.tradePlatformApi = tradePlatformApi;
    }
}