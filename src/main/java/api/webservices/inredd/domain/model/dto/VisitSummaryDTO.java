package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Visit;
import java.time.LocalDate;

public class VisitSummaryDTO {

    private Long id;
    private LocalDate visitDate;
    private String mainComplaint;

    // No-argument constructor
    public VisitSummaryDTO() {
    }

    // Mapping constructor that takes the Entity
    public VisitSummaryDTO(Visit visit) {
        this.id = visit.getId();
        this.visitDate = visit.getVisitDate();
        this.mainComplaint = visit.getMainComplaint();
    }

    // Getters and Setters
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
}