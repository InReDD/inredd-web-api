package api.webservices.inredd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import api.webservices.inredd.domain.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
