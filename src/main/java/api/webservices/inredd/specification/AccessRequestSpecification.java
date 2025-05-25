package api.webservices.inredd.specification;

import org.springframework.data.jpa.domain.Specification;
import api.webservices.inredd.domain.model.AccessRequest;

public class AccessRequestSpecification {
    public static Specification<AccessRequest> hasTerm(String term) {
        return (root, query, cb) -> {
            if (term == null || term.isBlank()) return null;
            String like = "%" + term.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("email")), like),
                cb.like(cb.lower(root.get("firstName")), like),
                cb.like(cb.lower(root.get("solution")), like)
            );
        };
    }
}