package api.webservices.inredd.repository;

import java.util.List;
import java.time.Instant;
import org.springframework.data.jpa.repository.*;
import api.webservices.inredd.domain.model.AccessRequest;
import java.util.Optional;

public interface AccessRequestRepository
        extends JpaRepository<AccessRequest, Long>, JpaSpecificationExecutor<AccessRequest> {
    Optional<AccessRequest> findByRequestToken(String token);

    // filtra por createdAt dentro dos últimos 7 dias:
    boolean existsByEmailAndConsumedAtIsNullAndCreatedAtAfter(String email, Instant cutoff);

    List<AccessRequest> findByEmail(String email);
}