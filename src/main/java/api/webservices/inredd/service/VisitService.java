package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.SexEnum;
import api.webservices.inredd.domain.model.Visit;
import api.webservices.inredd.domain.model.dto.AdvancedSearchResultDTO;
import api.webservices.inredd.domain.model.dto.VisitCreateDTO;
import api.webservices.inredd.domain.model.dto.VisitDTO;
import api.webservices.inredd.domain.model.dto.VisitSearchCriteriaDTO;
import api.webservices.inredd.domain.model.dto.VisitUpdateDTO;
import api.webservices.inredd.repository.PatientRepository;
import api.webservices.inredd.repository.VisitRepository;
import api.webservices.inredd.service.exception.ResourceNotFoundException;
import api.webservices.inredd.specification.VisitSpecification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final EntityManager entityManager; 

    public VisitService(VisitRepository visitRepository, PatientRepository patientRepository, EntityManager entityManager) {
        this.visitRepository = visitRepository;
        this.patientRepository = patientRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public VisitDTO getVisitById(Long id) {
        Visit visit = visitRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
        return new VisitDTO(visit);
    }

    @Transactional(readOnly = true)
    public List<VisitDTO> getAllVisitsForPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        List<Visit> visits = visitRepository.findAllByPatientIdWithDetails(patientId);
        return visits.stream().map(VisitDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public VisitDTO createVisit(Long patientId, VisitCreateDTO visitCreateDTO) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot create visit: Patient not found with id: " + patientId));

        Visit visit = new Visit();
        visit.setPatient(patient);
        visit.setVisitDate(visitCreateDTO.getVisitDate());
        visit.setMainComplaint(visitCreateDTO.getMainComplaint());

        Visit savedVisit = visitRepository.save(visit);
        return new VisitDTO(savedVisit);
    }
    
    @Transactional
    public VisitDTO updateVisit(Long id, VisitUpdateDTO visitUpdateDTO) {
        Visit existingVisit = visitRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));

        existingVisit.setVisitDate(visitUpdateDTO.getVisitDate());
        existingVisit.setMainComplaint(visitUpdateDTO.getMainComplaint());

        if (visitUpdateDTO.getAnamnesisForm() != null && existingVisit.getAnamnesisForm() != null) {
            var formDTO = visitUpdateDTO.getAnamnesisForm();
            var formEntity = existingVisit.getAnamnesisForm();
            
            formEntity.setWeightKg(formDTO.getWeightKg());
            formEntity.setHeightM(formDTO.getHeightM());

            if (formDTO.getSpecificHealthQuestions() != null && formEntity.getSpecificHealthQuestions() != null) {
                var questionsDTO = formDTO.getSpecificHealthQuestions();
                var questionsEntity = formEntity.getSpecificHealthQuestions();
                questionsEntity.setHasCardiovascularIssue(questionsDTO.getHasCardiovascularIssue());
            }
        }
        
        Visit updatedVisit = visitRepository.save(existingVisit);
        return new VisitDTO(updatedVisit);
    }

    @Transactional
    public void deleteVisit(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Visit not found with id: " + id);
        }
        visitRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AdvancedSearchResultDTO searchVisits(VisitSearchCriteriaDTO criteria) {
        // 1. Build the dynamic query specification from the criteria
        Specification<Visit> spec = VisitSpecification.createSpecification(criteria);

        // 2. Execute a custom query that combines the specification with fetch joins
        List<Visit> filteredVisits = findWithDetails(spec);

        // 3. Map the entity results to DTOs for the response
        List<VisitDTO> visitResultsDTO = filteredVisits.stream()
                .map(VisitDTO::new)
                .collect(Collectors.toList());

        // 4. Calculate statistics based on the filtered results
        Map<String, Object> stats = calculateStats(filteredVisits);

        // 5. Return the combined results and stats
        return new AdvancedSearchResultDTO(visitResultsDTO, stats);
    }


    private List<Visit> findWithDetails(Specification<Visit> spec) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Visit> query = builder.createQuery(Visit.class);
        Root<Visit> root = query.from(Visit.class);

        root.fetch("patient", JoinType.LEFT);
        root.fetch("anamnesisForm", JoinType.LEFT);

        // Apply the dynamic filters from the specification
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, builder);
            query.where(predicate);
        }

        // Use distinct to prevent duplicate Visit objects from multiple 'fetch' joins
        query.select(root).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * A helper method to calculate various statistics from a list of visits.
     *
     * @param visits The list of visits to analyze.
     * @return A map where keys are stat names and values are the calculated results.
     */
    private Map<String, Object> calculateStats(List<Visit> visits) {
        Map<String, Object> stats = new HashMap<>();

        // Example Stat: Count visits by patient sex
        Map<SexEnum, Long> visitsBySex = visits.stream()
                .filter(visit -> visit.getPatient() != null && visit.getPatient().getSex() != null)
                .collect(Collectors.groupingBy(
                        visit -> visit.getPatient().getSex(),
                        Collectors.counting()
                ));
        stats.put("visitsBySex", visitsBySex);
        
        // Example Stat: Count of visits with cardiovascular issues
        long cardiovascularCount = visits.stream()
            .filter(v -> v.getAnamnesisForm() != null && 
                         v.getAnamnesisForm().getSpecificHealthQuestions() != null &&
                         Boolean.TRUE.equals(v.getAnamnesisForm().getSpecificHealthQuestions().getHasCardiovascularIssue()))
            .count();
        stats.put("cardiovascularIssueCount", cardiovascularCount);


        return stats;
    }

}