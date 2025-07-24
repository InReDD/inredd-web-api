package api.webservices.inredd.domain.model.dto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.LocalDate;

/**
 * Data Transfer Object for creating a new Visit.
 */
public class VisitCreateDTO {

    @Null(message = "Visit date is required.")
    @FutureOrPresent(message = "Visit date must be in the present or a future date.")
    private LocalDate visitDate;

    @NotBlank(message = "Main complaint cannot be empty.")
    private String mainComplaint;
    private String radiographNotes; 
    private String viewerContextJson; 
    private byte[] radiographImageData; 
   
    private AnamnesisFormDTO anamnesisFormDTO;
    
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

    public String getRadiographNotes() {
        return radiographNotes;
    }

    public void setRadiographNotes(String radiographNotes) {
        this.radiographNotes = radiographNotes;
    }

    public String getViewerContextJson() {
        return viewerContextJson;
    }

    public void setViewerContextJson(String viewerContextJson) {
        this.viewerContextJson = viewerContextJson;
    }

    public byte[] getRadiographImageData() {
        return radiographImageData;
    }

    public void setRadiographImageData(byte[] radiographImageData) {
        this.radiographImageData = radiographImageData;
    }

    public AnamnesisFormDTO getAnamnesisFormDTO() {
        return anamnesisFormDTO;
    }

    public void setAnamnesisFormDTO(AnamnesisFormDTO anamnesisFormDTO) {
        this.anamnesisFormDTO = anamnesisFormDTO;
    }

}