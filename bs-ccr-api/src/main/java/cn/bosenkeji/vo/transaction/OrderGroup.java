package cn.bosenkeji.vo.transaction;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author CAJR
 * @date 2019/11/1 4:54 下午
 */
@JsonIgnoreProperties(value = {"handler"})
public class OrderGroup implements Serializable {

    @ApiModelProperty("订单组 id")
    private int id;

    @ApiModelProperty("订单组 name")
    private String name;

    @JSONField(name = "coin_pair_choice_id")
    @ApiModelProperty("自选币id")
    @JSONField(name = "coin_pair_choiceId")
    private int coinPairChoiceId;

    /**
    * 结单收益比
    */
    @JSONField(name = "end_profit_retio")
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

    @ApiModelProperty(hidden = true)
    private int status;

    /**
    * 订单组创建时间
    */
    @JSONField(name = "created_at")
    @ApiModelProperty(value = "订单组创建时间",hidden = true)
    private Timestamp createdAt;

    /**
    * 订单组更新时间
    */
    @JSONField(name = "updated_at")
    @ApiModelProperty(value = "订单组更新时间",hidden = true)
    private Timestamp updatedAt;

    /*非数据库字段*/
    @JSONField(name = "coin_pair_choice")
    @ApiModelProperty(hidden = true)
    private CoinPairChoice coinPairChoice;

    @ApiModelProperty(hidden = true)
    private List<TradeOrder> tradeOrders;

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

    public CoinPairChoice getCoinPairChoice() {
        return coinPairChoice;
    }

    public void setCoinPairChoice(CoinPairChoice coinPairChoice) {
        this.coinPairChoice = coinPairChoice;
    }

    public List<TradeOrder> getTradeOrders() {
        return tradeOrders;
    }

    public void setTradeOrders(List<TradeOrder> tradeOrders) {
        this.tradeOrders = tradeOrders;
    }
}