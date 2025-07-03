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
        * Finds a single patient by their ID and fetches all their associated data
        * graph
        *
        * @param id The ID of the patient.
        * @return
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
        *
        * @return A list of all Patients with their details.
        */
       @Query("SELECT DISTINCT p FROM Patient p " +
                     "LEFT JOIN FETCH p.visits v " +
                     "LEFT JOIN FETCH v.anamnesisForm af " +
                     "LEFT JOIN FETCH af.specificHealthQuestions " +
                     "LEFT JOIN FETCH v.radiographs")
       List<Patient> findAllWithDetails();

       /**
        * Finds the top 5 most recently created patients.
        * Spring Data JPA automatically derives the query from this method name.
        * "findTop5" -> Limits the result to 5.
        * "OrderByCreatedAtDesc" -> Orders by the 'createdAt' field in descending
        * order.
        * 
        * @return A list containing up to 5 of the newest patient records.
        */
       List<Patient> findTop5ByOrderByCreatedAtDesc();
}