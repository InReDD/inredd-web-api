package api.webservices.inredd.specification;

import javax.persistence.criteria.Predicate;
import api.webservices.inredd.domain.model.Paper;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class PaperSpecification {

    public static Specification<Paper> hasAuthor(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null || author.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            // Busca parcial e case-insensitive em array de autores
            return criteriaBuilder.like(
                    criteriaBuilder.function("array_to_string", String.class, root.get("authors"), criteriaBuilder.literal(',')),
                    "%" + author.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Paper> hasPublisher(String publisher) {
        return (root, query, criteriaBuilder) -> {
            if (publisher == null || publisher.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            // Busca parcial e case-insensitive no DOI (que pode conter o publisher)
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("doi")),
                    "%" + publisher.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Paper> hasTags(String type) {
        return (root, query, criteriaBuilder) -> {
            if (type == null || type.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            // Busca parcial e case-insensitive em array de tags
            return criteriaBuilder.like(
                    criteriaBuilder.function("array_to_string", String.class, root.get("tags"), criteriaBuilder.literal(',')),
                    "%" + type.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Paper> publishedBetween(String fromYear, String toYear) {
        return (root, query, criteriaBuilder) -> {
            if ((fromYear == null || fromYear.isEmpty()) && (toYear == null || toYear.isEmpty())) {
                return criteriaBuilder.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (fromYear != null && !fromYear.isEmpty()) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                criteriaBuilder.function("substring", String.class,
                                        root.get("publishDate"),
                                        criteriaBuilder.literal(1),
                                        criteriaBuilder.literal(4)
                                ),
                                fromYear
                        )
                );
            }

            if (toYear != null && !toYear.isEmpty()) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                criteriaBuilder.function("substring", String.class,
                                        root.get("publishDate"),
                                        criteriaBuilder.literal(1),
                                        criteriaBuilder.literal(4)
                                ),
                                toYear
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Busca o título quebrando o termo em palavras e fazendo um OR
     * case-insensitive  
     * Ex: "machine lear" → like '%machine%' OR like '%lear%'
     */
    public static Specification<Paper> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

}