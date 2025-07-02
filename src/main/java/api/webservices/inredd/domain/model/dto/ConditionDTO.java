package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.ConditionLookup;

public class ConditionDTO {

    private Long id;
    private String conditionName;
    private String category;

    // Default constructor
    public ConditionDTO() {}

    // Constructor from Entity
    public ConditionDTO(ConditionLookup entity) {
        this.id = entity.getId();
        this.conditionName = entity.getConditionName();
        this.category = entity.getCategory();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getConditionName() { return conditionName; }
    public void setConditionName(String conditionName) { this.conditionName = conditionName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}