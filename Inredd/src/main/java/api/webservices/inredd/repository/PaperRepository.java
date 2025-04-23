package api.webservices.inredd.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import api.webservices.inredd.domain.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long>, JpaSpecificationExecutor<Paper> {
}