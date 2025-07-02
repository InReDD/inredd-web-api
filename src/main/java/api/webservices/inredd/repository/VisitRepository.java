package api.webservices.inredd.repository;

import api.webservices.inredd.domain.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("SELECT v FROM Visit v LEFT JOIN FETCH v.anamnesisForm af LEFT JOIN FETCH af.specificHealthQuestions WHERE v.patient.id = :patientId")
    List<Visit> findAllByPatientIdWithDetails(Long patientId);
}