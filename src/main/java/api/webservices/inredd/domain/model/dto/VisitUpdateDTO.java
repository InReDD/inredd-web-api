package api.webservices.inredd.domain.model.dto;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * Data Transfer Object for updating an existing Visit.
 * Its structure mirrors the editable form on the frontend,
 * including nested objects for a complete update in one request.
 */
public class VisitUpdateDTO {

    private LocalDate visitDate;
    
    private String mainComplaint;
    
    @Valid // This annotation triggers validation on the nested object
    private AnamnesisFormDTO anamnesisForm;

    // --- Getters and Setters ---

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
}