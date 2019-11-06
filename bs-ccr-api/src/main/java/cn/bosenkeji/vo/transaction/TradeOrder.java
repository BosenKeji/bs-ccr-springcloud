package cn.bosenkeji.vo.transaction;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * @author CAJR
 * @date 2019/11/1 4:54 下午
 */
@JsonIgnoreProperties(value = {"handler"})
public class TradeOrder {

    @ApiModelProperty("订单id")
    private int id;

    @JSONField(name="order_group_id")
    @ApiModelProperty("订单组 id")
    private int orderGroupId;

    /**
    * 交易均价
    */
    @JSONField(name = "trade_average_price")
    @ApiModelProperty("交易均价")
    private Double tradeAveragePrice;

    /**
    * 交易数量
    */
    @JSONField(name = "trade_numbers")
    @ApiModelProperty("交易数量")
    private Double tradeNumbers;

    /**
    * 交易费用
    */
    @JSONField(name = "trade_cost")
    @ApiModelProperty("交易费用")
    private Double tradeCost;

    /**
    * 卖出收益
    */
    @JSONField(name = "shell_profit")
    @ApiModelProperty("卖出收益")
    private Double shellProfit;

    /**
    * 交易方式
    */
    @JSONField(name = "trade_type")
    @ApiModelProperty("交易方式")
    private int tradeType;

    @ApiModelProperty(value = "状态",hidden = true)
    private int status;

    /**
    * 订单创建时间
    */
    @JSONField(name = "created_at")
    @ApiModelProperty(value = "订单创建时间")
    private Timestamp createdAt;

    /**
    * 订单更新时间
    */
    @JSONField(name = "updated_at")
    @ApiModelProperty(value = "订单更新时间",hidden = true)
    private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(int orderGroupId) {
        this.orderGroupId = orderGroupId;
    }


    public Double getTradeAveragePrice() {
        return tradeAveragePrice;
    }

    public void setTradeAveragePrice(Double tradeAveragePrice) {
        this.tradeAveragePrice = tradeAveragePrice;
    }

    public Double getTradeNumbers() {
        return tradeNumbers;
    }

    public void setTradeNumbers(Double tradeNumbers) {
        this.tradeNumbers = tradeNumbers;
    }

    public Double getTradeCost() {
        return tradeCost;
    }

    public void setTradeCost(Double tradeCost) {
        this.tradeCost = tradeCost;
    }

    public Double getShellProfit() {
        return shellProfit;
    }

    public void setShellProfit(Double shellProfit) {
        this.shellProfit = shellProfit;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
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
}