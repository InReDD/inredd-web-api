package api.webservices.inredd.domain.model.dto;

import java.time.LocalDate;

import api.webservices.inredd.domain.model.Visit;

public class VisitDTO {
    private Long id;
    private LocalDate visitDate;
    private String mainComplaint;
    private AnamnesisFormDTO anamnesisForm;
    private RadiographDTO radiograph; 

    public VisitDTO(Visit entity) {
        this.id = entity.getId();
        this.visitDate = entity.getVisitDate();
        this.mainComplaint = entity.getMainComplaint();
        if (entity.getAnamnesisForm() != null) {
            this.anamnesisForm = new AnamnesisFormDTO(entity.getAnamnesisForm());
        }
        if (entity.getRadiograph() != null) {
            this.radiograph = new RadiographDTO(entity.getRadiograph());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getMainComplaint() {
        return mainComplaint;
    }

    public void setMainComplaint(String mainComplaint) {
        this.mainComplaint = mainComplaint;
    }

    public AnamnesisFormDTO getAnamnesisForm() {
        return anamnesisForm;
    }

    public void setAnamnesisForm(AnamnesisFormDTO anamnesisForm) {
        this.anamnesisForm = anamnesisForm;
    }

    public RadiographDTO getRadiograph() {
        return radiograph;
    }

    public void setRadiograph(RadiographDTO radiograph) {
        this.radiograph = radiograph;
    }
}