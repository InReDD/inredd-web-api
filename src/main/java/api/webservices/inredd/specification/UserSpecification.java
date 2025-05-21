package api.webservices.inredd.domain.specification;

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.Group;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import java.util.List;

public class UserSpecification {

    /** Se groupIds for nulo ou vazio, não aplica filtro. */
    public static Specification<User> inGroups(List<Long> groupIds) {
        return (root, query, builder) -> {
            if (groupIds == null || groupIds.isEmpty()) {
                return null;
            }
            // root.join gera INNER JOIN user.groups g
            Join<User, Group> join = root.join("groups");
            return join.get("idGroups").in(groupIds);
        };
    }

    public static Specification<User> hasName(String name) {
        return (root, query, builder) -> {
            if (name == null || name.isBlank()) {
                return null;
            }
            String pattern = "%" + name.toLowerCase() + "%";
            Expression<String> first = builder.lower(root.get("firstName"));
            Expression<String> last  = builder.lower(root.get("lastName"));
            return builder.or(
                builder.like(first, pattern),
                builder.like(last,  pattern)
            );
        };
    }
}
