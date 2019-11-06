package cn.bosenkeji.vo.transaction;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;


public class OpenSearchOrderVo extends TradeOrder{

    @ApiModelProperty("订单组 name")
    private String name;


    @ApiModelProperty("自选币id")
    @JSONField(name = "coin_pair_choice_id")
    private int coinPairChoiceId;

    /**
     * 结单收益比
     */
    @JSONField(name = "end_profit_ratio")
    @ApiModelProperty("结单收益比")
    private Double endProfitRatio;

    /**
     * 是否结单
     */
    @JSONField(name = "is_end")
    @ApiModelProperty("是否结单")
    private int isEnd;

    /**
     * 结单方式
     */
    @JSONField(name = "end_type")
    @ApiModelProperty("结单方式")
    private int endType;

    /**
     * 订单组创建时间
     */
    @JSONField(name = "created_time")
    @ApiModelProperty(value = "订单组创建时间",hidden = true)
    private Timestamp createdTime;

    /*非数据库字段*/
    //@JSONField(name = "coin_pair_choice")
    @ApiModelProperty(hidden = true)
    private CoinPairChoice coinPairChoice;

    @ApiModelProperty(hidden = true)
    private String coin_pair_choice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoinPairChoiceId() {
        return coinPairChoiceId;
    }

    public void setCoinPairChoiceId(int coinPairChoiceId) {
        this.coinPairChoiceId = coinPairChoiceId;
    }

    public Double getEndProfitRatio() {
        return endProfitRatio;
    }

    public void setEndProfitRatio(Double endProfitRatio) {
        this.endProfitRatio = endProfitRatio;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public int getEndType() {
        return endType;
    }

    public void setEndType(int endType) {
        this.endType = endType;
    }

    public CoinPairChoice getCoinPairChoice() {
        return coinPairChoice;
    }

    public void setCoinPairChoice(CoinPairChoice coinPairChoice) {
        this.coinPairChoice = coinPairChoice;
    }

    public String getCoin_pair_choice() {
        return coin_pair_choice;
    }

    public void setCoin_pair_choice(String coin_pair_choice) {
        this.coin_pair_choice = coin_pair_choice;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
