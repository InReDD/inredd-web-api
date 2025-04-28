package api.webservices.inredd.repository.activity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import api.webservices.inredd.domain.model.Activity;
import api.webservices.inredd.repository.filter.ActivityFilter;

public class ActivityRepositoryImpl implements ActivityRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Activity> filter(ActivityFilter activityFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Activity> criteria = builder.createQuery(Activity.class);
		Root<Activity> root = criteria.from(Activity.class);
		
		Predicate[] predicates = createConstraints(activityFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Activity> query = manager.createQuery(criteria);
		addPaginationConstraints(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, totalPages(activityFilter));
	}

	private Predicate[] createConstraints(ActivityFilter activityFilter, CriteriaBuilder builder, Root<Activity> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(activityFilter.getUser() != null) {
			predicates.add(builder.equal(
					root.get("user"), activityFilter.getUser()));
		}
		
		if(activityFilter.getType() != null) {
			predicates.add(builder.equal(
					root.get("type"), activityFilter.getType()));
		}
		
		if (activityFilter.getInitialDate() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get("data"), activityFilter.getInitialDate()));
		}
		
		if (activityFilter.getFinalDate() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get("data"), activityFilter.getFinalDate()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addPaginationConstraints(TypedQuery<Activity> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRecordsPerPage = pageable.getPageSize();
		int firstPageRecord = currentPage * totalRecordsPerPage;
		
		query.setFirstResult(firstPageRecord);
		query.setMaxResults(totalRecordsPerPage);
	}
	
	private Long totalPages(ActivityFilter activityFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Activity> root = criteria.from(Activity.class);
		
		Predicate[] predicates = createConstraints(activityFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}
