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

    public PatientService(PatientRepository patientRepository, VisitRepository visitRepository) {
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patient -> new PatientDTO(patient, false)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PatientDTO getPatientWithDetailsById(Long patientId) {
        List<Patient> patients = patientRepository.findByIdWithDetails(patientId);
        if (patients.isEmpty()) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        Patient patient = patients.get(0);
        return new PatientDTO(patient, true);
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
    public Patient createPatient(PatientCreateDTO patientCreateDTO) {
        Patient patient = new Patient();
        patient.setFullName(patientCreateDTO.getFullName());
        patient.setDateOfBirth(patientCreateDTO.getDateOfBirth());
        patient.setSex(patientCreateDTO.getSex());
        patient.setAddress(patientCreateDTO.getAddress());
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient updatePatient(Long id, PatientCreateDTO patientUpdateDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        existingPatient.setFullName(patientUpdateDTO.getFullName());
        existingPatient.setDateOfBirth(patientUpdateDTO.getDateOfBirth());
        // existingPatient.setSex(patientUpdateDTO.getSex());
        existingPatient.setAddress(patientUpdateDTO.getAddress());

        return patientRepository.save(existingPatient);
    }

    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}