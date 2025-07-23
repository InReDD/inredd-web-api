package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Radiograph;

import java.time.LocalDate;

public class RadiographDTO {

    private Long id;
    private LocalDate radiographDate;
    private String viewerContextJson;
    private String notes;
    private byte[] imageData;

    public RadiographDTO(Radiograph radiograph) {
        this.id = radiograph.getId();
        this.viewerContextJson = radiograph.getViewerContextJson();
        this.notes = radiograph.getNotes();
        this.imageData = radiograph.getImageData();
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