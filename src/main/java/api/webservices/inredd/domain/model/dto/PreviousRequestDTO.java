package api.webservices.inredd.domain.model.dto;

import java.time.Instant;

public class PreviousRequestDTO {
    private Long id;
    private Instant createdAt;
    private String solution;
    private String state; // pending | accepted | rejected
    private String moderationReason;
    private String moderatorName;
    private Instant moderationDate;

    public PreviousRequestDTO() {}

    public PreviousRequestDTO(Long id, Instant createdAt, String solution, String state,
                             String moderationReason, String moderatorName, Instant moderationDate) {
        this.id = id;
        this.createdAt = createdAt;
        this.solution = solution;
        this.state = state;
        this.moderationReason = moderationReason;
        this.moderatorName = moderatorName;
        this.moderationDate = moderationDate;
    }

    // GETTERS E SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getModerationReason() { return moderationReason; }
    public void setModerationReason(String moderationReason) { this.moderationReason = moderationReason; }

    public String getModeratorName() { return moderatorName; }
    public void setModeratorName(String moderatorName) { this.moderatorName = moderatorName; }

    public Instant getModerationDate() { return moderationDate; }
    public void setModerationDate(Instant moderationDate) { this.moderationDate = moderationDate; }
}
