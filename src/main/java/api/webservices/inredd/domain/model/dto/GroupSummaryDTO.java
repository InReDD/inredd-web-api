package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Group;

public class GroupSummaryDTO {

    private Long idGroups;
    private String name;
    private String description;

    public GroupSummaryDTO() {
    }

    public GroupSummaryDTO(Group group) {
        this.idGroups = group.getIdGroups();
        this.name = group.getName();
        this.description = group.getDescription();
    }

    // Getters e Setters
    public Long getIdGroups() {
        return idGroups;
    }

    public void setIdGroups(Long idGroups) {
        this.idGroups = idGroups;
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
}