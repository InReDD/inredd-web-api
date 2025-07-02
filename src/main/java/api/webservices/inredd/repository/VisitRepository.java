package api.webservices.inredd.repository;

import api.webservices.inredd.domain.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
       /**
     * Finds a single visit by ID and fetches all its related entities in one query
     * to prevent N+1 problems.
     *
     * @param id The ID of the visit.
     * @return An Optional containing the Visit with all details, if found.
     */
    @Query("SELECT v FROM Visit v " +
           "LEFT JOIN FETCH v.anamnesisForm af " +
           "LEFT JOIN FETCH af.specificHealthQuestions " +
           "LEFT JOIN FETCH af.conditions " + // Assuming a 'conditions' Set in AnamnesisForm
           "LEFT JOIN FETCH v.radiographs " +
           "WHERE v.id = :id")
    Optional<Visit> findByIdWithDetails(@Param("id") Long id);

    /**
     * Finds all visits for a given patient, fetching all details for each visit.
     *
     * @param patientId The ID of the patient.
     * @return A List of fully detailed Visit objects.
     */
    @Query("SELECT v FROM Visit v LEFT JOIN FETCH v.anamnesisForm af LEFT JOIN FETCH af.specificHealthQuestions WHERE v.patient.id = :patientId")
    List<Visit> findAllByPatientIdWithDetails(Long patientId);
}