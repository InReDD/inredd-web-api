package api.webservices.inredd.domain.model.dto;
import java.time.Instant;

public class PrivacyPolicyDTO {
    private String content;
    private Boolean accepted;
    private Instant acceptedAt;

    public PrivacyPolicyDTO(String content, Boolean accepted, Instant acceptedAt) {
        this.content    = content;
        this.accepted   = accepted;
        this.acceptedAt = acceptedAt;
    }
    public String getContent()     { return content; }
    public Boolean getAccepted()   { return accepted; }
    public Instant getAcceptedAt() { return acceptedAt; }
}
