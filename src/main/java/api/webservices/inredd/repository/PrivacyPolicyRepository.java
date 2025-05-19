package api.webservices.inredd.repository;
import api.webservices.inredd.domain.model.PrivacyPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivacyPolicyRepository extends JpaRepository<PrivacyPolicy,Long> {
    PrivacyPolicy findTopByOrderByCreatedAtDesc();
}