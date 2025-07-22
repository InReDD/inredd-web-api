package api.webservices.inredd.repository;

import api.webservices.inredd.domain.model.Radiograph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RadiographRepository extends JpaRepository<Radiograph, Long> {
    List<Radiograph> findAllByVisitId(Long visitId);
}