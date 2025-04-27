package api.webservices.inredd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.webservices.inredd.domain.model.Activity;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.repository.activity.ActivityRepositoryQuery;

public interface ActivityRepository extends 
	JpaRepository<Activity, Long>, ActivityRepositoryQuery {

	public List<Activity> findByUser(User user);
}
