package cn.bosenkeji.vo.permission;

import java.sql.Timestamp;

public class RolePermissionGroup {
    private int id;

    private int roleId;

    private int permissionGroupId;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public RolePermissionGroup(int id, int roleId, int permissionGroupId, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.roleId = roleId;
        this.permissionGroupId = permissionGroupId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public RolePermissionGroup() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(int permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
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