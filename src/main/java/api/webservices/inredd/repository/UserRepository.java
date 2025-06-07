package api.webservices.inredd.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import api.webservices.inredd.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	public Optional<User> findByEmail(String email);

	Optional<User> findByIdUser(Long idUser);

	List<User> findByGroupsIsEmpty();
    Page<User> findByGroupsIsEmpty(Pageable pageable);

}
