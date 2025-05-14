package api.webservices.inredd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import api.webservices.inredd.domain.model.Academic;

public interface AcademicRepository extends JpaRepository<Academic, Long> {
}