package cn.bosenkeji.vo.transaction;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * @author CAJR
 * @date 2019/11/6 7:22 下午
 */
public class OrderGroupOpenSearchFormat {
    @ApiModelProperty("订单组 id")
    @JSONField(name = "order_group_id")
    private int id;

    @ApiModelProperty("自选币id")
    @JSONField(name = "coin_pair_choice_id")
    private int coinPairChoiceId;

    @ApiModelProperty("订单组 name")
    private String name;

    @JSONField(name = "created_time")
    @ApiModelProperty(value = "订单组创建时间",hidden = true)
    private Timestamp createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinPairChoiceId() {
        return coinPairChoiceId;
    }

    public void setCoinPairChoiceId(int coinPairChoiceId) {
        this.coinPairChoiceId = coinPairChoiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
