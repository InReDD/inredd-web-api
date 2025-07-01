package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.Sex;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PatientDTO {

    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private Sex sex;
    private List<VisitSummaryDTO> visits;

    public PatientDTO() {
    }

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.fullName = patient.getFullName();
        this.dateOfBirth = patient.getDateOfBirth();
        this.sex = patient.getSex();
        
        this.visits = patient.getVisits().stream()
                .map(VisitSummaryDTO::new)
                .collect(Collectors.toList());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public List<VisitSummaryDTO> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitSummaryDTO> visits) {
        this.visits = visits;
    }
}