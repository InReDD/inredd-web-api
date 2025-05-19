package api.webservices.inredd.repository;
import api.webservices.inredd.domain.model.TermsOfService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsOfServiceRepository extends JpaRepository<TermsOfService,Long> {
    TermsOfService findTopByOrderByCreatedAtDesc();
}