package cn.bosenkeji.vo.permission;

import java.sql.Timestamp;

public class UserRole {
    private int id;

    private int userId;

    private int roleId;

    private int userType;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public UserRole(int id, int userId, int roleId, int userType, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.userType = userType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserRole() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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