package cn.bosenkeji.vo.transaction;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author CAJR
 * @date 2019/11/5 11:04 上午
 */

public class OrderGroupOther {
    @ApiModelProperty("订单组 id")
    private int id;

    @ApiModelProperty("订单组 name")
    private String name;

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
}
