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

    public VisitService(VisitRepository visitRepository, PatientRepository patientRepository,
            EntityManager entityManager) {
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cannot create visit: Patient not found with id: " + patientId));

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
            formEntity.setSystolicBp(formDTO.getSystolicBp());
            formEntity.setDiastolicBp(formDTO.getDiastolicBp());
            formEntity.setPregnant(formDTO.getIsPregnant());
            formEntity.setHadRecentFever(formDTO.getHadRecentFever());
            formEntity.setUnderMedicalTreatment(formDTO.getIsUnderMedicalTreatment());
            formEntity.setTakingMedication(formDTO.getIsTakingMedication());
            formEntity.setDetailedMedicalHistory(formDTO.getDetailedMedicalHistory());
            formEntity.setFamilyHealthHistory(formDTO.getFamilyHealthHistory());
            formEntity.setPreviousDentalHistory(formDTO.getPreviousDentalHistory());
            formEntity.setPsychosocialHistory(formDTO.getPsychosocialHistory());
            formEntity.setAdditionalInfoForDentist(formDTO.getAdditionalInfoForDentist());
            formEntity.setSpecialNeedsDuringTreatment(formDTO.getSpecialNeedsDuringTreatment());

            if (formDTO.getSpecificHealthQuestions() != null && formEntity.getSpecificHealthQuestions() != null) {
                var questionsDTO = formDTO.getSpecificHealthQuestions();
                var questionsEntity = formEntity.getSpecificHealthQuestions();

                questionsEntity.setHasCardiovascularIssue(questionsDTO.getHasCardiovascularIssue());
                questionsEntity.setHasRheumaticFever(questionsDTO.getHasRheumaticFever());
                questionsEntity.setHasJointPain(questionsDTO.getHasJointPain());
                questionsEntity.setHasChestPain(questionsDTO.getHasChestPain());
                questionsEntity.setHasFatigueOnExertion(questionsDTO.getHasFatigueOnExertion());
                questionsEntity.setHasAnkleEdema(questionsDTO.getHasAnkleEdema());
                questionsEntity.setHasRecentWeightLoss(questionsDTO.getHasRecentWeightLoss());
                questionsEntity.setHadHepatitis(questionsDTO.getHadHepatitis());
                questionsEntity.setHasKidneyProblems(questionsDTO.getHasKidneyProblems());
                questionsEntity.setHasGastricProblems(questionsDTO.getHasGastricProblems());
                questionsEntity.setHasDizzinessFainting(questionsDTO.getHasDizzinessFainting());
                questionsEntity.setHasEpilepsy(questionsDTO.getHasEpilepsy());
                questionsEntity.setWasHospitalized(questionsDTO.getWasHospitalized());
                questionsEntity.setHasPersistentCough(questionsDTO.getHasPersistentCough());
                questionsEntity.setHadLocalAnesthesia(questionsDTO.getHadLocalAnesthesia());
                questionsEntity.setHadAnesthesiaReaction(questionsDTO.getHadAnesthesiaReaction());
                questionsEntity.setHadGeneralAnesthesia(questionsDTO.getHadGeneralAnesthesia());
                questionsEntity.setHasExcessiveBleeding(questionsDTO.getHasExcessiveBleeding());
                questionsEntity.setBleedingControlMethod(questionsDTO.getBleedingControlMethod());
                questionsEntity.setHadDentalTreatmentComplication(questionsDTO.getHadDentalTreatmentComplication());
                questionsEntity.setTookPenicillin(questionsDTO.getTookPenicillin());
                questionsEntity.setTookCorticosteroidLast12m(questionsDTO.getTookCorticosteroidLast12m());
                questionsEntity.setHasAllergies(questionsDTO.getHasAllergies());
                questionsEntity.setHadMedicationRelatedProblem(questionsDTO.getHadMedicationRelatedProblem());
                questionsEntity.setUsesSubstances(questionsDTO.getUsesSubstances());
                questionsEntity.setHadOralWhiteSpots(questionsDTO.getHadOralWhiteSpots());
                questionsEntity.setWhiteSpotsTreatment(questionsDTO.getWhiteSpotsTreatment());
                questionsEntity.setHasRecurrentAphthousUlcers(questionsDTO.getHasRecurrentAphthousUlcers());
                questionsEntity.setHadHivTest(questionsDTO.getHadHivTest());
                questionsEntity.setHasInsensitiveBodyArea(questionsDTO.getHasInsensitiveBodyArea());
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
        Specification<Visit> spec = VisitSpecification.createSpecification(criteria);

        List<Visit> filteredVisits = findWithDetails(spec);

        List<VisitDTO> visitResultsDTO = filteredVisits.stream()
                .map(VisitDTO::new)
                .collect(Collectors.toList());

        Map<String, Object> stats = calculateStats(filteredVisits);

        return new AdvancedSearchResultDTO(visitResultsDTO, stats);
    }

    private List<Visit> findWithDetails(Specification<Visit> spec) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Visit> query = builder.createQuery(Visit.class);
        Root<Visit> root = query.from(Visit.class);

        root.fetch("patient", JoinType.LEFT);
        root.fetch("anamnesisForm", JoinType.LEFT);

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, builder);
            query.where(predicate);
        }

        query.select(root).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * A helper method to calculate various statistics from a list of visits.
     *
     * @param visits The list of visits to analyze.
     * @return A map where keys are stat names and values are the calculated
     *         results.
     */
    private Map<String, Object> calculateStats(List<Visit> visits) {
        Map<String, Object> stats = new HashMap<>();

        Map<SexEnum, Long> visitsBySex = visits.stream()
                .filter(visit -> visit.getPatient() != null && visit.getPatient().getSex() != null)
                .collect(Collectors.groupingBy(
                        visit -> visit.getPatient().getSex(),
                        Collectors.counting()));
        stats.put("visitsBySex", visitsBySex);

        long cardiovascularCount = visits.stream()
                .filter(v -> v.getAnamnesisForm() != null &&
                        v.getAnamnesisForm().getSpecificHealthQuestions() != null &&
                        Boolean.TRUE
                                .equals(v.getAnamnesisForm().getSpecificHealthQuestions().getHasCardiovascularIssue()))
                .count();
        stats.put("cardiovascularIssueCount", cardiovascularCount);

        return stats;
    }

}