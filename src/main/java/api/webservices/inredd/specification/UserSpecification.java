package api.webservices.inredd.domain.specification;

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.Group;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import java.util.List;

public class UserSpecification {

    public static Specification<User> inGroups(List<Long> groupIds) {
        return (root, query, builder) -> {
            if (groupIds == null || groupIds.isEmpty()) {
                // Filtra usuários que têm pelo menos um grupo (INNER JOIN)
                // O join força que só venham usuários com associação na tabela de junção
                root.join("groups");
                return builder.conjunction(); // equivalente a "where 1=1", mas só usuários que têm grupo entram por causa do join
            }
            // Se groupIds foi informado, filtra normalmente
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
