package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.Visit;
import api.webservices.inredd.domain.model.dto.VisitCreateDTO;
import api.webservices.inredd.domain.model.dto.VisitDTO;
import api.webservices.inredd.domain.model.dto.VisitUpdateDTO;
import api.webservices.inredd.repository.PatientRepository;
import api.webservices.inredd.repository.VisitRepository;
import api.webservices.inredd.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    // You might need a mapper for complex updates
    // private final VisitMapper visitMapper;

    public VisitService(VisitRepository visitRepository, PatientRepository patientRepository) {
        this.visitRepository = visitRepository;
        this.patientRepository = patientRepository;
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

        // Update top-level fields
        existingVisit.setVisitDate(visitUpdateDTO.getVisitDate());
        existingVisit.setMainComplaint(visitUpdateDTO.getMainComplaint());

        // Handle complex nested update (a mapper would be ideal here)
        if (visitUpdateDTO.getAnamnesisForm() != null && existingVisit.getAnamnesisForm() != null) {
            // Manual mapping for demonstration
            var formDTO = visitUpdateDTO.getAnamnesisForm();
            var formEntity = existingVisit.getAnamnesisForm();
            
            formEntity.setWeightKg(formDTO.getWeightKg());
            formEntity.setHeightM(formDTO.getHeightM());
            // ... map ALL other fields from AnamnesisFormDTO to the entity ...

            if (formDTO.getSpecificHealthQuestions() != null && formEntity.getSpecificHealthQuestions() != null) {
                var questionsDTO = formDTO.getSpecificHealthQuestions();
                var questionsEntity = formEntity.getSpecificHealthQuestions();
                questionsEntity.setHasCardiovascularIssue(questionsDTO.getHasCardiovascularIssue());
                // ... map ALL other boolean fields ...
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
}