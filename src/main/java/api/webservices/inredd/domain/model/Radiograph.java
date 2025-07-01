package api.webservices.inredd.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "radiographs")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Radiograph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "radiograph_date", nullable = false)
    private LocalDate radiographDate;

    @Column(name = "file_path_or_url", nullable = false, length = 1024)
    private String filePathOrUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // @Type(type = "org.hibernate.type.StringNVarchartype") // For older hibernate versions, or use a converter
    // @Column(name = "viewer_context_json", columnDefinition = "jsonb")
    // private String viewerContextJson;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id")
    private Visit visit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getRadiographDate() { return radiographDate; }
    public void setRadiographDate(LocalDate radiographDate) { this.radiographDate = radiographDate; }
    public String getFilePathOrUrl() { return filePathOrUrl; }
    public void setFilePathOrUrl(String filePathOrUrl) { this.filePathOrUrl = filePathOrUrl; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    // public String getViewerContextJson() { return viewerContextJson; }
    // public void setViewerContextJson(String viewerContextJson) { this.viewerContextJson = viewerContextJson; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public Visit getVisit() { return visit; }
    public void setVisit(Visit visit) { this.visit = visit; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Radiograph other = (Radiograph) obj;
        return Objects.equals(id, other.id);
    }
}