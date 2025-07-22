package api.webservices.inredd.domain.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "radiographs")
public class Radiograph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @Column(nullable = false)
    private LocalDate radiographDate;

    @Column(columnDefinition = "JSONB")
    private String viewerContextJson;


    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt = LocalDate.now();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPatientId() {
        throw new UnsupportedOperationException("Unimplemented method 'getPatientId'");
    }

    public void setPatientId(Long patientId) {
        throw new UnsupportedOperationException("Unimplemented method 'setPatientId'");
    }
}