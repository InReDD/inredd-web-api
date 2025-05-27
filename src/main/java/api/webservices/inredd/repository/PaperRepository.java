package api.webservices.inredd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import api.webservices.inredd.domain.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long>, JpaSpecificationExecutor<Paper> {

    // Busca por autor usando array_to_string
    @Query(
            value = "SELECT * FROM paper p WHERE LOWER(array_to_string(p.authors, ',')) LIKE LOWER(concat('%', :author, '%'))",
            nativeQuery = true
    )
    Page<Paper> findByAuthorContaining(@Param("author") String author, Pageable pageable);

    // Busca por título
    @Query(
            value = "SELECT * FROM paper p WHERE LOWER(p.title) LIKE LOWER(concat('%', :title, '%'))",
            nativeQuery = true
    )
    Page<Paper> findByTitleContaining(@Param("title") String title, Pageable pageable);

    // Busca por tipo/tag usando array_to_string
    @Query(
            value = "SELECT * FROM paper p WHERE LOWER(array_to_string(p.tags, ',')) LIKE LOWER(concat('%', :type, '%'))",
            nativeQuery = true
    )
    Page<Paper> findByTypeContaining(@Param("type") String type, Pageable pageable);

    // Busca por intervalo de anos
    @Query(
            value = "SELECT * FROM paper p WHERE SUBSTRING(p.publish_date, 1, 4) BETWEEN :fromYear AND :toYear",
            nativeQuery = true
    )
    Page<Paper> findByPublishDateBetweenYears(
            @Param("fromYear") String fromYear,
            @Param("toYear") String toYear,
            Pageable pageable
    );

    // Busca combinada (autor, título, tipo, intervalo de anos)
    @Query(
            value = "SELECT * FROM paper p WHERE " +
                    "(:author IS NULL OR LOWER(array_to_string(p.authors, ',')) LIKE LOWER(concat('%', :author, '%'))) AND " +
                    "(:title IS NULL OR contains_all_words(p.title, :title)) AND " +
                    "(:type IS NULL OR LOWER(array_to_string(p.tags, ',')) LIKE LOWER(concat('%', :type, '%'))) AND " +
                    "((:fromYear IS NULL AND :toYear IS NULL) OR " +
                    "(:fromYear IS NULL AND SUBSTRING(p.publish_date, 1, 4) <= :toYear) OR " +
                    "(:toYear IS NULL AND SUBSTRING(p.publish_date, 1, 4) >= :fromYear) OR " +
                    "(SUBSTRING(p.publish_date, 1, 4) BETWEEN :fromYear AND :toYear))",
            nativeQuery = true
    )
    Page<Paper> searchPapers(
            @Param("author") String author,
            @Param("title") String title,
            @Param("type") String type,
            @Param("fromYear") String fromYear,
            @Param("toYear") String toYear,
            Pageable pageable
    );

    // Listar autores únicos
    @Query(
            value = "SELECT DISTINCT unnest(authors) FROM paper",
            nativeQuery = true
    )
    List<String> findAllUniqueAuthors();

    // Listar autores únicos por intervalo de anos
    @Query(
            value = "SELECT DISTINCT unnest(authors) FROM paper " +
                    "WHERE (:fromYear IS NULL OR :toYear IS NULL OR SUBSTRING(publish_date, 1, 4) BETWEEN :fromYear AND :toYear)",
            nativeQuery = true
    )
    List<String> findUniqueAuthorsByYearRange(
            @Param("fromYear") String fromYear,
            @Param("toYear") String toYear
    );
}