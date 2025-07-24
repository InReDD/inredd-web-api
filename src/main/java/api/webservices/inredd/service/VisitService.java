package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.AnamnesisForm;
import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.Radiograph;
import api.webservices.inredd.domain.model.SexEnum;
import api.webservices.inredd.domain.model.SpecificHealthQuestions;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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

    @Value("${inredd.webservice.api.endpoint}")
    private String webserviceAPI;

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
    public VisitDTO createVisit(Long patientId, VisitCreateDTO visitCreateDTO, MultipartFile radiographImage) {
        // Validate that the patient exists
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cannot create visit: Patient not found with id: " + patientId));

        // Create a new Visit entity
        Visit visit = new Visit();
        visit.setPatient(patient); // Associate the visit with the patient

        // Set the visit date
        if (visitCreateDTO != null && visitCreateDTO.getVisitDate() != null) {
            visit.setVisitDate(visitCreateDTO.getVisitDate());
        } else {
            visit.setVisitDate(LocalDate.now()); // Default to today's date if not provided
        }

        // Set the main complaint
        if (visitCreateDTO != null && visitCreateDTO.getMainComplaint() != null) {
            visit.setMainComplaint(visitCreateDTO.getMainComplaint());
        } else {
            visit.setMainComplaint("No main complaint provided"); // Default value
        }

        // Handle Anamnesis Form
        AnamnesisForm formEntity = new AnamnesisForm();
        if (visitCreateDTO != null && visitCreateDTO.getAnamnesisFormDTO() != null) {
            var formDTO = visitCreateDTO.getAnamnesisFormDTO();

            formEntity.setWeightKg(formDTO.getWeightKg() != null ? formDTO.getWeightKg() : java.math.BigDecimal.ZERO);
            formEntity.setHeightM(formDTO.getHeightM() != null ? formDTO.getHeightM() : java.math.BigDecimal.ZERO);
            formEntity.setSystolicBp(formDTO.getSystolicBp() != null ? formDTO.getSystolicBp() : 0);
            formEntity.setDiastolicBp(formDTO.getDiastolicBp() != null ? formDTO.getDiastolicBp() : 0);
            formEntity.setIsPregnant(formDTO.getIsPregnant() != null ? formDTO.getIsPregnant() : false);
            formEntity.setHadRecentFever(formDTO.getHadRecentFever() != null ? formDTO.getHadRecentFever() : false);
            formEntity.setIsUnderMedicalTreatment(
                    formDTO.getIsUnderMedicalTreatment() != null ? formDTO.getIsUnderMedicalTreatment() : false);
            formEntity.setIsTakingMedication(
                    formDTO.getIsTakingMedication() != null ? formDTO.getIsTakingMedication() : false);
            formEntity.setDetailedMedicalHistory(
                    formDTO.getDetailedMedicalHistory() != null ? formDTO.getDetailedMedicalHistory() : "");
            formEntity.setFamilyHealthHistory(
                    formDTO.getFamilyHealthHistory() != null ? formDTO.getFamilyHealthHistory() : "");
            formEntity.setPreviousDentalHistory(
                    formDTO.getPreviousDentalHistory() != null ? formDTO.getPreviousDentalHistory() : "");
            formEntity.setPsychosocialHistory(
                    formDTO.getPsychosocialHistory() != null ? formDTO.getPsychosocialHistory() : "");
            formEntity.setAdditionalInfoForDentist(
                    formDTO.getAdditionalInfoForDentist() != null ? formDTO.getAdditionalInfoForDentist() : "");
            formEntity.setSpecialNeedsDuringTreatment(
                    formDTO.getSpecialNeedsDuringTreatment() != null ? formDTO.getSpecialNeedsDuringTreatment() : "");

            // Handle Specific Health Questions
            if (formDTO.getSpecificHealthQuestions() != null) {
                var questionsDTO = formDTO.getSpecificHealthQuestions();
                SpecificHealthQuestions questionsEntity = new SpecificHealthQuestions();

                questionsEntity.setHasCardiovascularIssue(
                        questionsDTO.getHasCardiovascularIssue() != null ? questionsDTO.getHasCardiovascularIssue()
                                : false);
                questionsEntity.setHasRheumaticFever(
                        questionsDTO.getHasRheumaticFever() != null ? questionsDTO.getHasRheumaticFever() : false);
                questionsEntity.setHasJointPain(
                        questionsDTO.getHasJointPain() != null ? questionsDTO.getHasJointPain() : false);
                questionsEntity.setHasChestPain(
                        questionsDTO.getHasChestPain() != null ? questionsDTO.getHasChestPain() : false);
                questionsEntity.setHasFatigueOnExertion(
                        questionsDTO.getHasFatigueOnExertion() != null ? questionsDTO.getHasFatigueOnExertion()
                                : false);
                questionsEntity.setHasAnkleEdema(
                        questionsDTO.getHasAnkleEdema() != null ? questionsDTO.getHasAnkleEdema() : false);
                questionsEntity.setHasRecentWeightLoss(
                        questionsDTO.getHasRecentWeightLoss() != null ? questionsDTO.getHasRecentWeightLoss() : false);
                questionsEntity.setHadHepatitis(
                        questionsDTO.getHadHepatitis() != null ? questionsDTO.getHadHepatitis() : false);
                questionsEntity.setHasKidneyProblems(
                        questionsDTO.getHasKidneyProblems() != null ? questionsDTO.getHasKidneyProblems() : false);
                questionsEntity.setHasGastricProblems(
                        questionsDTO.getHasGastricProblems() != null ? questionsDTO.getHasGastricProblems() : false);
                questionsEntity.setHasDizzinessFainting(
                        questionsDTO.getHasDizzinessFainting() != null ? questionsDTO.getHasDizzinessFainting()
                                : false);
                questionsEntity
                        .setHasEpilepsy(questionsDTO.getHasEpilepsy() != null ? questionsDTO.getHasEpilepsy() : false);
                questionsEntity.setWasHospitalized(
                        questionsDTO.getWasHospitalized() != null ? questionsDTO.getWasHospitalized() : false);
                questionsEntity.setHasPersistentCough(
                        questionsDTO.getHasPersistentCough() != null ? questionsDTO.getHasPersistentCough() : false);
                questionsEntity.setHadLocalAnesthesia(
                        questionsDTO.getHadLocalAnesthesia() != null ? questionsDTO.getHadLocalAnesthesia() : false);
                questionsEntity.setHadAnesthesiaReaction(
                        questionsDTO.getHadAnesthesiaReaction() != null ? questionsDTO.getHadAnesthesiaReaction()
                                : false);
                questionsEntity.setHadGeneralAnesthesia(
                        questionsDTO.getHadGeneralAnesthesia() != null ? questionsDTO.getHadGeneralAnesthesia()
                                : false);
                questionsEntity.setHasExcessiveBleeding(
                        questionsDTO.getHasExcessiveBleeding() != null ? questionsDTO.getHasExcessiveBleeding()
                                : false);
                questionsEntity.setBleedingControlMethod(
                        questionsDTO.getBleedingControlMethod() != null ? questionsDTO.getBleedingControlMethod() : "");
                questionsEntity
                        .setHadDentalTreatmentComplication(questionsDTO.getHadDentalTreatmentComplication() != null
                                ? questionsDTO.getHadDentalTreatmentComplication()
                                : false);
                questionsEntity.setTookPenicillin(
                        questionsDTO.getTookPenicillin() != null ? questionsDTO.getTookPenicillin() : false);
                questionsEntity.setTookCorticosteroidLast12m(questionsDTO.getTookCorticosteroidLast12m() != null
                        ? questionsDTO.getTookCorticosteroidLast12m()
                        : false);
                questionsEntity.setHasAllergies(
                        questionsDTO.getHasAllergies() != null ? questionsDTO.getHasAllergies() : false);
                questionsEntity.setHadMedicationRelatedProblem(questionsDTO.getHadMedicationRelatedProblem() != null
                        ? questionsDTO.getHadMedicationRelatedProblem()
                        : false);
                questionsEntity.setUsesSubstances(
                        questionsDTO.getUsesSubstances() != null ? questionsDTO.getUsesSubstances() : false);
                questionsEntity.setHadOralWhiteSpots(
                        questionsDTO.getHadOralWhiteSpots() != null ? questionsDTO.getHadOralWhiteSpots() : false);
                questionsEntity.setWhiteSpotsTreatment(
                        questionsDTO.getWhiteSpotsTreatment() != null ? questionsDTO.getWhiteSpotsTreatment() : "");
                questionsEntity.setHasRecurrentAphthousUlcers(questionsDTO.getHasRecurrentAphthousUlcers() != null
                        ? questionsDTO.getHasRecurrentAphthousUlcers()
                        : false);
                questionsEntity
                        .setHadHivTest(questionsDTO.getHadHivTest() != null ? questionsDTO.getHadHivTest() : false);
                questionsEntity.setHasInsensitiveBodyArea(
                        questionsDTO.getHasInsensitiveBodyArea() != null ? questionsDTO.getHasInsensitiveBodyArea()
                                : false);

                formEntity.setSpecificHealthQuestions(questionsEntity);
            }
        } else {
            // Set default values for Anamnesis Form
            formEntity.setWeightKg(java.math.BigDecimal.ZERO);
            formEntity.setHeightM(java.math.BigDecimal.ZERO);
            formEntity.setSystolicBp(0);
            formEntity.setDiastolicBp(0);
            formEntity.setIsPregnant(false);
            formEntity.setHadRecentFever(false);
            formEntity.setIsUnderMedicalTreatment(false);
            formEntity.setIsTakingMedication(false);
            formEntity.setDetailedMedicalHistory("");
            formEntity.setFamilyHealthHistory("");
            formEntity.setPreviousDentalHistory("");
            formEntity.setPsychosocialHistory("");
            formEntity.setAdditionalInfoForDentist("");
            formEntity.setSpecialNeedsDuringTreatment("");

            // Set default values for Specific Health Questions
            SpecificHealthQuestions questionsEntity = new SpecificHealthQuestions();
            questionsEntity.setHasCardiovascularIssue(false);
            questionsEntity.setHasRheumaticFever(false);
            questionsEntity.setHasJointPain(false);
            questionsEntity.setHasChestPain(false);
            questionsEntity.setHasFatigueOnExertion(false);
            questionsEntity.setHasAnkleEdema(false);
            questionsEntity.setHasRecentWeightLoss(false);
            questionsEntity.setHadHepatitis(false);
            questionsEntity.setHasKidneyProblems(false);
            questionsEntity.setHasGastricProblems(false);
            questionsEntity.setHasDizzinessFainting(false);
            questionsEntity.setHasEpilepsy(false);
            questionsEntity.setWasHospitalized(false);
            questionsEntity.setHasPersistentCough(false);
            questionsEntity.setHadLocalAnesthesia(false);
            questionsEntity.setHadAnesthesiaReaction(false);
            questionsEntity.setHadGeneralAnesthesia(false);
            questionsEntity.setHasExcessiveBleeding(false);
            questionsEntity.setBleedingControlMethod("");
            questionsEntity.setHadDentalTreatmentComplication(false);
            questionsEntity.setTookPenicillin(false);
            questionsEntity.setTookCorticosteroidLast12m(false);
            questionsEntity.setHasAllergies(false);
            questionsEntity.setHadMedicationRelatedProblem(false);
            questionsEntity.setUsesSubstances(false);
            questionsEntity.setHadOralWhiteSpots(false);
            questionsEntity.setWhiteSpotsTreatment("");
            questionsEntity.setHasRecurrentAphthousUlcers(false);
            questionsEntity.setHadHivTest(false);
            questionsEntity.setHasInsensitiveBodyArea(false);

            // Set the relationship in both directions
            questionsEntity.setAnamnesisForm(formEntity);
            formEntity.setSpecificHealthQuestions(questionsEntity);
        }

        // Associate the AnamnesisForm with the Visit
        formEntity.setVisit(visit); // Set the relationship in both directions
        visit.setAnamnesisForm(formEntity);

        // Save the visit to the database
        Visit savedVisit = visitRepository.save(visit);

        // Return the saved visit as a DTO
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

            formEntity.setSystolicBp(formDTO.getSystolicBp());
            formEntity.setDiastolicBp(formDTO.getDiastolicBp());
            formEntity.setIsPregnant(formDTO.getIsPregnant());
            formEntity.setHadRecentFever(formDTO.getHadRecentFever());
            formEntity.setIsUnderMedicalTreatment(formDTO.getIsUnderMedicalTreatment());
            formEntity.setIsTakingMedication(formDTO.getIsTakingMedication());
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

    /**
     * Helper method to fetch visits with details using a Specification.
     */
    @Transactional(readOnly = true)
    public List<Visit> findWithDetails(Specification<Visit> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Visit> query = cb.createQuery(Visit.class);
        Root<Visit> root = query.from(Visit.class);

        // Join fetch associations as needed (example: patient, radiograph, etc.)
        root.fetch("patient", JoinType.LEFT);
        root.fetch("radiograph", JoinType.LEFT);

        Predicate predicate = spec != null ? spec.toPredicate(root, query, cb) : cb.conjunction();
        query.where(predicate);

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

    private JsonNode processRadiograph(MultipartFile radiographImage) {
        try {
            // Create a RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Prepare the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(radiographImage.getBytes()) {
                @Override
                public String getFilename() {
                    return radiographImage.getOriginalFilename();
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send the request to the external API
            ResponseEntity<String> response = restTemplate.postForEntity(webserviceAPI, requestEntity, String.class);

            // Check the response status and return the results
            if (response.getStatusCode().is2xxSuccessful()) {
                // Parse the response body into a JSON string
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readTree(response.getBody());
            } else {
                throw new RuntimeException(
                        "Failed to process radiograph with external service: " + response.getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read radiograph image", e);
        }
    }
}