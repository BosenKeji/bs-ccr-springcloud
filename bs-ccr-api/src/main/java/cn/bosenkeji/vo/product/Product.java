package cn.bosenkeji.vo.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author: xivinChen
 */
@Api
@Validated
public class Product implements Serializable {
    private int id;

    @NotBlank(message = "产品名称不能为空")
    private String name;

    @NotBlank(message = "产品版本名称不能为空")
    private String versionName;

    private String logo;

    private String remark;

    private int status;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;

    public Product(int id, String name, String versionName, String logo, String remark, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.versionName = versionName;
        this.logo = logo;
        this.remark = remark;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product() {
        super();
    }

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

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", versionName='" + versionName + '\'' +
                ", logo='" + logo + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}