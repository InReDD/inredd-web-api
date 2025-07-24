package api.webservices.inredd.domain.model;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

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

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private JsonNode viewerContextJson;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "image_data", nullable = true)
    private byte[] imageData;

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
    public JsonNode getViewerContextJson() {
        return viewerContextJson != null ? viewerContextJson : JsonNodeFactory.instance.objectNode();
    }

    public void setViewerContextJson(JsonNode viewerContextJson) {
        this.viewerContextJson = viewerContextJson;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}