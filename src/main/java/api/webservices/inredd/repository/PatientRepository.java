package api.webservices.inredd.repository;

import api.webservices.inredd.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}