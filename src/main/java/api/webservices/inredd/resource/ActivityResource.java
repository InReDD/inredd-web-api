package api.webservices.inredd.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.webservices.inredd.domain.model.Activity;
import api.webservices.inredd.repository.ActivityRepository;
import api.webservices.inredd.repository.filter.ActivityFilter;
import api.webservices.inredd.service.ActivityService;

@RestController
@RequestMapping("/activities")
public class ActivityResource {

	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private ActivityService activityService;
	
	@GetMapping
	public Page<Activity> search(ActivityFilter activityFilter, Pageable pageable){
		return activityService.search(activityFilter, pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Activity> findById(@PathVariable Long id){
		Optional<Activity> activity = 
				activityRepository.findById(id);
		if(activity.isPresent()) {
			return ResponseEntity.ok(activity.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/user/{email}")
	public ResponseEntity<List<Activity>> listByUser(@PathVariable String email){
		List<Activity> activities = activityService.listByUser(email);
		if(!activities.isEmpty()) {
			return ResponseEntity.ok(activities);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Activity create(@Valid @RequestBody Activity activity) {
		return activityService.save(activity);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		activityRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Activity> update(@PathVariable Long id, @Valid @RequestBody Activity activity) {
		Activity activitySaved = activityService.update(id, activity);
		return ResponseEntity.ok(activitySaved);
	}
}
