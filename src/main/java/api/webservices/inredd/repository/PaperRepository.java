package api.webservices.inredd.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import api.webservices.inredd.domain.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long>, JpaSpecificationExecutor<Paper> {

    @Query(
      "SELECT p " +
      "FROM Paper p " +
      "WHERE FUNCTION('year', p.publishDate) BETWEEN :fromYear AND :toYear"
    )
    List<Paper> findByPublishDateBetweenYears(
        @Param("fromYear") int fromYear,
        @Param("toYear")   int toYear
    );
}