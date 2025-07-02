package api.webservices.inredd.domain.model.dto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating a new Visit.
 * Contains only the essential fields needed for creation.
 */
public class VisitCreateDTO {

    @Null(message = "Visit date is required.")
    @FutureOrPresent(message = "Visit date must be in the present or a future date.")
    private LocalDate visitDate;

    @NotBlank(message = "Main complaint cannot be empty.")
    private String mainComplaint;

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
}