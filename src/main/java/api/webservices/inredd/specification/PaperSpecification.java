package api.webservices.inredd.specification;

import javax.persistence.criteria.*;
import api.webservices.inredd.domain.model.Paper;
import org.springframework.data.jpa.domain.Specification;

public class PaperSpecification {

    public static Specification<Paper> hasAuthor(String author) {
        return (root, query, cb) -> {
            if (author == null || author.isBlank()) {
                return cb.conjunction();
            }
            // array_to_string(authors, ',') → "J. Doe;E. Torres"
            Expression<String> arrAsString = cb.function(
                "array_to_string",
                String.class,
                root.get("authors"),
                cb.literal(";")           // use ';' ou ',' conforme seu separador
            );
            return cb.like(
                cb.lower(arrAsString),
                "%" + author.toLowerCase() + "%"
            );
        };
    }
    public static Specification<Paper> hasPublisher(String publisher) {
        return (root, query, builder) ->
            publisher == null ? null : builder.like(builder.lower(root.get("publisher")), "%" + publisher.toLowerCase() + "%");
    }

    public static Specification<Paper> hasType(String type) {
        return (root, query, builder) ->
            type == null ? null : builder.equal(builder.lower(root.get("type")), type.toLowerCase());
    }

    public static Specification<Paper> publishedBetween(String fromYear, String toYear) {
        return (root, query, builder) -> {
            if (fromYear == null || toYear == null) return null;
            return builder.between(root.get("publishDate"), fromYear, toYear);
        };
    }

    /**
     * Busca o título quebrando o termo em palavras e fazendo um OR
     * case-insensitive  
     * Ex: "machine lear" → like '%machine%' OR like '%lear%'
     */
    public static Specification<Paper> hasTitle(String title) {
        return (Root<Paper> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (title == null || title.trim().isEmpty()) {
                return cb.conjunction();
            }
            // quebra a string em palavras
            String[] terms = title.trim().split("\\s+");
            // inicia um OR
            Predicate predicate = cb.disjunction();
            for (String term : terms) {
                // LOWER(title) LIKE %term%
                predicate = cb.or(
                  predicate,
                  cb.like(
                    cb.lower(root.get("title")),
                    "%" + term.toLowerCase() + "%"
                  )
                );
            }
            return predicate;
        };
    }

    public static Specification<Paper> hasTag(String tag) {
        return (root, query, cb) -> {
            if (tag == null || tag.isBlank()) {
                return cb.conjunction();
            }
            Expression<String> arrAsString = cb.function(
                "array_to_string",
                String.class,
                root.get("tags"),
                cb.literal(",")
            );
            return cb.like(
                cb.lower(arrAsString),
                "%" + tag.toLowerCase() + "%"
            );
        };
    }
}