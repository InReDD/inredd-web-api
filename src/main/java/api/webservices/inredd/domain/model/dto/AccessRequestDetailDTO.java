package api.webservices.inredd.domain.model.dto;

import java.time.Instant;
import java.util.List;

public class AccessRequestDetailDTO {
    private Long id;
    private String email;
    private String firstName;
    private String institution;
    private String solution;
    private String reason;
    private Instant createdAt;
    private Instant expiresAt;
    private boolean completed;
    private String state;            // pending | accepted | rejected
    private String moderationReason; // motivo da recusa
    private String moderatorName;    // se houver
    private Instant moderationDate;
    private List<PreviousRequestDTO> previousRequests;

    public AccessRequestDetailDTO() {}

    public AccessRequestDetailDTO(
            Long id,
            String email,
            String firstName,
            String institution,
            String solution,
            String reason,
            Instant createdAt,
            Instant expiresAt,
            boolean completed,
            String state,
            String moderationReason,
            String moderatorName,
            Instant moderationDate,
            List<PreviousRequestDTO> previousRequests
    ) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.institution = institution;
        this.solution = solution;
        this.reason = reason;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.completed = completed;
        this.state = state;
        this.moderationReason = moderationReason;
        this.moderatorName = moderatorName;
        this.moderationDate = moderationDate;
        this.previousRequests = previousRequests;
    }

    // GETTERS E SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getModerationReason() { return moderationReason; }
    public void setModerationReason(String moderationReason) { this.moderationReason = moderationReason; }

    public String getModeratorName() { return moderatorName; }
    public void setModeratorName(String moderatorName) { this.moderatorName = moderatorName; }

    public Instant getModerationDate() { return moderationDate; }
    public void setModerationDate(Instant moderationDate) { this.moderationDate = moderationDate; }

    public List<PreviousRequestDTO> getPreviousRequests() { return previousRequests; }
    public void setPreviousRequests(List<PreviousRequestDTO> previousRequests) { this.previousRequests = previousRequests; }
}
