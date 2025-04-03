package api.webservices.inredd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.webservices.inredd.domain.model.Activity;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.repository.ActivityRepository;
import api.webservices.inredd.repository.UserRepository;
import api.webservices.inredd.repository.filter.ActivityFilter;
import api.webservices.inredd.service.exception.NonExistentOrInactiveUserException;

@Service
public class ActivityService {
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Activity save(Activity activity) {
		Optional<User> user = userRepository.findById(
				activity.getUser().getId());
		if(!user.isPresent() || !user.get().isActive()) {
			throw new NonExistentOrInactiveUserException();
		}
		return activityRepository.save(activity);
	}
	
	public List<Activity> listByUser(String email){
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			return activityRepository.findByUser(user.get());
		}
		return null;
	}
	
	public Activity update(Long id, Activity activity) {
		Activity activitySaved = findActivityById(id);
		BeanUtils.copyProperties(activity, activitySaved, "id");
		return activityRepository.save(activitySaved);
	}
	
	public Activity findActivityById(Long id) {
		Activity activitySaved = activityRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return activitySaved;
	}
	
	public Page<Activity> search(ActivityFilter activityFilter, Pageable pageable){
		return activityRepository.filter(activityFilter, pageable);
	}
	
}
