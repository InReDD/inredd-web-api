
package api.webservices.inredd.domain.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "terms_of_service")
public class TermsOfService {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTermsOfService;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Long getIdTermsOfService() { return idTermsOfService; }
    public void setIdTermsOfService(Long id) { this.idTermsOfService = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
