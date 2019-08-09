package cn.bosenkeji.vo.tradeplatform;

import cn.bosenkeji.vo.coin.CoinPair;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;

    /**非数据库字段*/
    @ApiModelProperty(hidden = true)
    private List<CoinPair> coinPairs;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private List<TradePlatformCoinPair> tradePlatformCoinPairs;

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

    public List<CoinPair> getCoinPairs() {
        return coinPairs;
    }

    public void setCoinPairs(List<CoinPair> coinPairs) {
        this.coinPairs = coinPairs;
    }

    public List<TradePlatformCoinPair> getTradePlatformCoinPairs() {
        return tradePlatformCoinPairs;
    }

    public void setTradePlatformCoinPairs(List<TradePlatformCoinPair> tradePlatformCoinPairs) {
        this.tradePlatformCoinPairs = tradePlatformCoinPairs;
    }
}