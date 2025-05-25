package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.AccessRequest;
import java.time.Instant;

public class AccessRequestDTO {

    private Long id;
    private String email;
    private String firstName;
    private String solution;
    private String reason;
    private Instant createdAt;
    private Instant expiresAt;
    private boolean completed;

    private Long   userId;
    private String institution;

    public AccessRequestDTO(AccessRequest ar) {
        this.id = ar.getId();
        this.email = ar.getEmail();
        this.firstName = ar.getFirstName();
        this.solution = ar.getSolution();
        this.reason = ar.getReason();
        this.createdAt = ar.getCreatedAt();
        this.expiresAt = ar.getExpiresAt();
        this.completed = ar.getConsumedAt() != null;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSolution() {
        return solution;
    }

    public String getReason() {
        return reason;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
