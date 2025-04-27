package api.webservices.inredd.repository.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import api.webservices.inredd.domain.model.Activity;
import api.webservices.inredd.repository.filter.ActivityFilter;

public interface ActivityRepositoryQuery {

	Page<Activity> filter(ActivityFilter activityFilter, Pageable pageable);
	
}
