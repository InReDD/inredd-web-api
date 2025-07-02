package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.Visit;
import api.webservices.inredd.repository.PatientRepository;
import api.webservices.inredd.repository.VisitRepository;
import api.webservices.inredd.domain.model.dto.PatientCreateDTO;
import api.webservices.inredd.domain.model.dto.PatientDTO;
import api.webservices.inredd.domain.model.dto.VisitDTO;
import api.webservices.inredd.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;

    // The AnamnesisFormRepository is no longer needed directly here,
    // as we fetch its data through the Patient and Visit repositories.
    public PatientService(PatientRepository patientRepository, VisitRepository visitRepository) {
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
    }

    /**
     * Retrieves a list of all patients with their full details, including visits and anamnesis.
     * Uses an efficient query to prevent N+1 problems.
     *
     * @return A list of detailed PatientDTOs.
     */
    @Transactional(readOnly = true)
    public List<PatientDTO> getAllPatientsWithDetails() {
        List<Patient> patients = patientRepository.findAllWithDetails();
        return patients.stream().map(PatientDTO::new).collect(Collectors.toList());
    }

    /**
     * Retrieves a single patient by their ID, with all associated visits, anamnesis forms,
     * and specific health questions included in the response.
     *
     * @param patientId The ID of the patient to retrieve.
     * @return A comprehensive PatientDTO with all nested data.
     */
    @Transactional(readOnly = true)
    public PatientDTO getPatientWithDetailsById(Long patientId) {
        Patient patient = patientRepository.findByIdWithDetails(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        return new PatientDTO(patient);
    }

    /**
     * Retrieves all visits for a specific patient.
     * This method is more focused than getPatientWithDetailsById if you only need visit data.
     *
     * @param patientId The ID of the patient.
     * @return A list of VisitDTOs with nested anamnesis data.
     */
    @Transactional(readOnly = true)
    public List<VisitDTO> getAllVisitsForPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        // Assuming visitRepository.findAllByPatientIdWithDetails still exists and is optimized
        List<Visit> visits = visitRepository.findAllByPatientIdWithDetails(patientId);
        return visits.stream().map(VisitDTO::new).collect(Collectors.toList());
    }


    /**
     * Creates a new patient from a DTO.
     *
     * @param patientCreateDTO The DTO containing the creation data.
     * @return The newly created and saved Patient entity.
     */
    @Transactional
    public Patient createPatient(PatientCreateDTO patientCreateDTO) {
        Patient patient = new Patient();
        patient.setFullName(patientCreateDTO.getFullName());
        patient.setDateOfBirth(patientCreateDTO.getDateOfBirth());
        patient.setSex(patientCreateDTO.getSex());
        patient.setAddress(patientCreateDTO.getAddress());
        return patientRepository.save(patient);
    }

    /**
     * Updates an existing patient's demographic data from a DTO.
     *
     * @param id               The ID of the patient to update.
     * @param patientUpdateDTO The DTO containing the update data.
     * @return The updated Patient entity.
     */
    @Transactional
    public Patient updatePatient(Long id, PatientCreateDTO patientUpdateDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        existingPatient.setFullName(patientUpdateDTO.getFullName());
        existingPatient.setDateOfBirth(patientUpdateDTO.getDateOfBirth());
        existingPatient.setSex(patientUpdateDTO.getSex());
        existingPatient.setAddress(patientUpdateDTO.getAddress());

        return patientRepository.save(existingPatient);
    }

    /**
     * Deletes a patient and all their associated data (due to CASCADE settings).
     *
     * @param id The ID of the patient to delete.
     */
    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}