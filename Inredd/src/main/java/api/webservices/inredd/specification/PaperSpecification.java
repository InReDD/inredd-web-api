package api.webservices.inredd.specification;

import api.webservices.inredd.domain.model.Paper;
import org.springframework.data.jpa.domain.Specification;

public class PaperSpecification {

    public static Specification<Paper> hasAuthor(String author) {
        return (root, query, builder) ->
            author == null ? null : builder.like(builder.lower(root.get("authors")), "%" + author.toLowerCase() + "%");
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
}