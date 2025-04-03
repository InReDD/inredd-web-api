package api.webservices.inredd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.webservices.inredd.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findByEmail(String email);

}
