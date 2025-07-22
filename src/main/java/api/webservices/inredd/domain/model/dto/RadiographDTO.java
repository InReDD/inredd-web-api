package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Radiograph;

import java.time.LocalDate;

public class RadiographDTO {

    private Long id;
    private Long visitId;
    private Long patientId;
    private LocalDate radiographDate;
    private String viewerContextJson;
    private String notes;
    private byte[] imageData;

    public RadiographDTO(Radiograph radiograph) {
        this.id = radiograph.getId();
        this.visitId = radiograph.getVisit().getId();
        this.patientId = radiograph.getPatientId();
        this.radiographDate = radiograph.getRadiographDate();
        this.viewerContextJson = radiograph.getViewerContextJson();
        this.notes = radiograph.getNotes();
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getRadiographDate() {
        return radiographDate;
    }

    public void setRadiographDate(LocalDate radiographDate) {
        this.radiographDate = radiographDate;
    }

    public String getViewerContextJson() {
        return viewerContextJson;
    }

    public void setViewerContextJson(String viewerContextJson) {
        this.viewerContextJson = viewerContextJson;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}