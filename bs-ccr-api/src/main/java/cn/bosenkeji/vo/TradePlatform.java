package cn.bosenkeji.vo;

import io.swagger.annotations.ApiModelProperty;

import java.beans.Transient;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/15 15:43
 */

public class TradePlatform {
    @ApiModelProperty("交易平台id")
    private int id;

    @ApiModelProperty("交易平台name")
    private String name;

    @ApiModelProperty("交易平台图片url")
    private String logo;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    /*非数据库字段*/
    private TradePlatformApi tradePlatformApi;

    private List<CoinPair> coinPairs;

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
    public List<CoinPair> getCoinPairs() {
        return coinPairs;
    }

    public void setCoinPairs(List<CoinPair> coinPairs) {
        this.coinPairs = coinPairs;
    }


}