package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.Radiograph;
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

        // Handle optional visitCreateDTO
        if (visitCreateDTO != null) {
            visit.setVisitDate(visitCreateDTO.getVisitDate() != null ? visitCreateDTO.getVisitDate() : LocalDate.now()); // Set the visit date or default to today
            visit.setMainComplaint(visitCreateDTO.getMainComplaint()); // Set the main complaint
        } else {
            visit.setVisitDate(LocalDate.now()); // Default to today's date if visitCreateDTO is null
        }

        // Handle optional radiograph creation and association
        if (radiographImage != null && !radiographImage.isEmpty()) {
            try {
                Radiograph radiograph = new Radiograph();

                // Associate the radiograph with the patient
                radiograph.setPatient(patient);

                radiograph.setImageData(radiographImage.getBytes()); // Save the image data
                if (visitCreateDTO != null) {
                    radiograph.setNotes(visitCreateDTO.getRadiographNotes()); // Set notes from DTO
                }

                // Send the image to the external service and get the results
                JsonNode viewerContextJson = processRadiograph(radiographImage);
                radiograph.setViewerContextJson(viewerContextJson); // Save the results in viewerContextJson

                visit.setRadiograph(radiograph); // Associate the radiograph with the visit
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload radiograph image", e);
            }
        }

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
                throw new RuntimeException("Failed to process radiograph with external service: " + response.getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read radiograph image", e);
        }
    }
}