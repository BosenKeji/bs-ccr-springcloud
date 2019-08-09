package cn.bosenkeji.vo.coin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/9 16:27
 */

public class CoinPair implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("货币对 id")
    private int id;

    @ApiModelProperty("货币对 名称")
    private String name;

    @ApiModelProperty("是否流行")
    private  int isPopular;

    @ApiModelProperty("是否官方推荐")
    private int isOfficialSet;

    @ApiModelProperty("货币对状态")
    private int status;

    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;


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

    public int getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(int isPopular) {
        this.isPopular = isPopular;
    }

    public int getIsOfficialSet() {
        return isOfficialSet;
    }

    public void setIsOfficialSet(int isOfficialSet) {
        this.isOfficialSet = isOfficialSet;
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

    @Override
    public String toString() {
        return "CoinPair{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPopular=" + isPopular +
                ", isOfficialSet=" + isOfficialSet +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}