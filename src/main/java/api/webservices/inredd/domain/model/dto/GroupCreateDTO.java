package api.webservices.inredd.domain.model.dto;

import java.util.List;

public class GroupCreateDTO {
    private String name;
    private String description;
    private List<Long> permissionIds;

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Long> getPermissionIds() { return permissionIds; }
    public void setPermissionIds(List<Long> permissionIds) { this.permissionIds = permissionIds; }
}
