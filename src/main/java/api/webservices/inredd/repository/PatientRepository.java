package api.webservices.inredd.repository;

import api.webservices.inredd.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

       @Query("SELECT p FROM Patient p " +
       "LEFT JOIN FETCH p.visits v " +
       "WHERE p.id = :id")
       List<Patient> findByIdWithDetails(@Param("id") Long id);

       /**
        * Finds the top 5 most recently created patients.
        */
       List<Patient> findTop5ByOrderByCreatedAtDesc();

}