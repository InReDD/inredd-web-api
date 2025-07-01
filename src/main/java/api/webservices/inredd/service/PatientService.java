package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.repository.PatientRepository;
import api.webservices.inredd.domain.model.dto.PatientCreateDTO;
import api.webservices.inredd.domain.model.dto.PatientDTO;
import api.webservices.inredd.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // --- Read operations remain the same ---
    @Transactional(readOnly = true)
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(PatientDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id).map(PatientDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    /**
     * Creates a new patient from a DTO.
     * @param patientCreateDTO The DTO containing the creation data.
     * @return The newly created and saved Patient entity.
     */
    @Transactional
    public Patient createPatient(PatientCreateDTO patientCreateDTO) {
        // Manually map from the DTO to a new entity
        Patient patient = new Patient();
        patient.setFullName(patientCreateDTO.getFullName());
        patient.setDateOfBirth(patientCreateDTO.getDateOfBirth());
        patient.setSex(patientCreateDTO.getSex());
        patient.setAddress(patientCreateDTO.getAddress());

        return patientRepository.save(patient);
    }

    /**
     * Updates an existing patient from a DTO.
     * @param id The ID of the patient to update.
     * @param patientUpdateDTO The DTO containing the update data.
     * @return The updated Patient entity.
     */
    @Transactional
    public Patient updatePatient(Long id, PatientCreateDTO patientUpdateDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        // Map the updated fields from the DTO to the existing entity
        existingPatient.setFullName(patientUpdateDTO.getFullName());
        existingPatient.setDateOfBirth(patientUpdateDTO.getDateOfBirth());
        existingPatient.setSex(patientUpdateDTO.getSex());
        existingPatient.setAddress(patientUpdateDTO.getAddress());

        return patientRepository.save(existingPatient);
    }

    /**
     * Deletes a patient by their ID.
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