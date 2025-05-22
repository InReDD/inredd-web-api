package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Permission;

public class PermissionDTO {

    private Long id;
    private String description;

    public PermissionDTO() {
    }

    public PermissionDTO(Permission permission) {
        this.id = permission.getId();
        this.description = permission.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}