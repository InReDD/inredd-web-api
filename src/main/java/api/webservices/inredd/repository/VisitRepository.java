package api.webservices.inredd.repository;

import api.webservices.inredd.domain.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
           "LEFT JOIN FETCH af.conditions " + 
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

    /**
     * Counts the number of visits within a given date range.
     * Spring Data JPA automatically derives the query from this method name.
     * "countBy" -> Specifies this is a count query.
     * "VisitDateBetween" -> Creates a WHERE clause for the 'visitDate' field using two parameters.
     * @param startDate The start of the date range (inclusive).
     * @param endDate The end of the date range (inclusive).
     * @return The total number of visits scheduled within the specified range.
     */
    long countByVisitDateBetween(LocalDate startDate, LocalDate endDate);
    
}