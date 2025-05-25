package api.webservices.inredd.domain.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "access_request")
public class AccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String solution;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "consumed_at")
    private Instant consumedAt;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="request_token", nullable = false, unique = true)
    private String requestToken;
  
    @Column(name="expires_at", nullable = false)
    private Instant expiresAt;

    /** Se for rejeitado, armazena o motivo */
    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getConsumedAt() {
        return consumedAt;
    }

    public void setConsumedAt(Instant consumedAt) {
        this.consumedAt = consumedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRequestToken() {
        return requestToken;
    }
    
    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
    
    public Instant getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getRejectionReason() {
        return rejectionReason; 
    }
    
    public void setRejectionReason(String rejectionReason) { 
        this.rejectionReason = rejectionReason; 
    }
}
