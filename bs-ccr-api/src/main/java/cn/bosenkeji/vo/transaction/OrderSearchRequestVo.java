package cn.bosenkeji.vo.transaction;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class OrderSearchRequestVo {


    @ApiModelProperty(hidden = true)
    private Long startTime;
    @ApiModelProperty(hidden = true)
    private Long endTime;


    private String coinPairChoiceIds;
    private Integer tradeType;


    public String getCoinPairChoiceIds() {
        return coinPairChoiceIds;
    }

    public void setCoinPairChoiceIds(String coinPairChoiceIds) {
        this.coinPairChoiceIds = coinPairChoiceIds;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }
}
