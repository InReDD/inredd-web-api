package api.webservices.inredd.repository;

import api.webservices.inredd.domain.model.AnamnesisForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface AnamnesisFormRepository extends JpaRepository<AnamnesisForm, Long> {
    @Query("SELECT af FROM AnamnesisForm af LEFT JOIN FETCH af.specificHealthQuestions WHERE af.visit.id = :visitId")
    Optional<AnamnesisForm> findByVisitIdWithDetails(Long visitId);
}