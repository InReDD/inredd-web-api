package api.webservices.inredd.domain.model.dto;

import java.util.List;

public class GroupCreateDTO {
    private String name;
    private String description;
    private List<Long> permissionIds;
    private List<String> permissionNames;

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Long> getPermissionIds() { return permissionIds; }
    public void setPermissionIds(List<Long> permissionIds) { this.permissionIds = permissionIds; }

    public List<String> getPermissionNames() { return permissionNames; }
    public void setPermissionNames(List<String> permissionNames) { this.permissionNames = permissionNames; }
}
