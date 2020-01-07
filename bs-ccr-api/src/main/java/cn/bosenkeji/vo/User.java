package cn.bosenkeji.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author CAJR
 * @create 2019/7/17 10:34
 */

@Validated
public class User implements Serializable {

    private int id;

    private String username;

    @Size(min = 11,max = 11)
    private String tel;

    private String password;

    @ApiModelProperty(hidden = true)
    private int status;

    private int isBinding;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    private Timestamp createdAt;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getIsBinding() {
        return isBinding;
    }

    public void setIsBinding(int isBinding) {
        this.isBinding = isBinding;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", tel='" + tel + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", isBinding=" + isBinding +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}