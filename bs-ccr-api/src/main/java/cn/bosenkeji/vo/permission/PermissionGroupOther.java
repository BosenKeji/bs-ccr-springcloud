package cn.bosenkeji.vo.permission;

import cn.bosenkeji.vo.permission.Permission;

import java.util.List;

public class PermissionGroupOther {

    private int groupId;
    private String name;
    private String description;

    private List<Permission> permissionList;

    public PermissionGroupOther() { }

    public PermissionGroupOther(int groupId, String name, String description, List<Permission> permissionList) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.permissionList = permissionList;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "PermissionGroupOther{" +
                "groupId=" + groupId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", permissionList=" + permissionList +
                '}';
    }
}
