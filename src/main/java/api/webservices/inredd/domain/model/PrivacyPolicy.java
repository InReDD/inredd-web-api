
package api.webservices.inredd.domain.model;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "privacy_policy")
public class PrivacyPolicy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrivacyPolicy;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Long getIdPrivacyPolicy() { return idPrivacyPolicy; }
    public void setIdPrivacyPolicy(Long id) { this.idPrivacyPolicy = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}