package api.webservices.inredd.repository;

import api.webservices.inredd.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Finds a single patient by their ID and fetches all their associated data graph
     * (visits, anamnesis forms, specific health questions, etc.) in one go.
     * Using LEFT JOIN FETCH ensures that patients without visits are still returned.
     *
     * @param id The ID of the patient.
     * @return An Optional containing the Patient with all details, if found.
     */
    @Query("SELECT p FROM Patient p " +
           "LEFT JOIN FETCH p.visits v " +
           "LEFT JOIN FETCH v.anamnesisForm af " +
           "LEFT JOIN FETCH af.specificHealthQuestions " +
           "LEFT JOIN FETCH v.radiographs " +
           "WHERE p.id = :id")
    Optional<Patient> findByIdWithDetails(@Param("id") Long id);

    /**
     * Finds all patients and fetches their complete associated data graph.
     * WARNING: This can be memory-intensive if you have many patients with extensive histories.
     * Use with caution in production environments.
     *
     * @return A list of all Patients with their details.
     */
    @Query("SELECT DISTINCT p FROM Patient p " +
           "LEFT JOIN FETCH p.visits v " +
           "LEFT JOIN FETCH v.anamnesisForm af " +
           "LEFT JOIN FETCH af.specificHealthQuestions " +
           "LEFT JOIN FETCH v.radiographs")
    List<Patient> findAllWithDetails();
}