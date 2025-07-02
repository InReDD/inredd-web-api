package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.SexEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set; // Use Set to match the entity
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private SexEnum sex;
    private String address;
    private OffsetDateTime createdAt;
    private Set<VisitDTO> visits;

    public PatientDTO(Patient entity) {
        this.id = entity.getId();
        this.fullName = entity.getFullName();
        this.dateOfBirth = entity.getDateOfBirth();
        this.sex = entity.getSex();
        this.address = entity.getAddress();
        this.createdAt = entity.getCreatedAt();

        if (entity.getVisits() != null && !entity.getVisits().isEmpty()) {
            this.visits = entity.getVisits().stream()
                    .map(VisitDTO::new)
                    .collect(Collectors.toSet());
        }
    }

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

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<VisitDTO> getVisits() {
        return visits;
    }

    public void setVisits(Set<VisitDTO> visits) {
        this.visits = visits;
    }
}