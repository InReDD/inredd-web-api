package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.dto.DashboardStatsDTO;
import api.webservices.inredd.domain.model.dto.PatientDTO;
import api.webservices.inredd.repository.PatientRepository;
import api.webservices.inredd.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;

    public DashboardService(PatientRepository patientRepository, VisitRepository visitRepository) {
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
    }

    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats() {
        long totalPatients = patientRepository.count();
        
        // Count visits scheduled for the upcoming 7 days
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        long upcomingVisits = visitRepository.countByVisitDateBetween(today, nextWeek);

        // Get the 5 most recently created patients
        List<Patient> recentPatientEntities = patientRepository.findTop5ByOrderByCreatedAtDesc();
        List<PatientDTO> recentPatientsDTO = recentPatientEntities.stream()
            .map(patient -> new PatientDTO(patient, false))
            .collect(Collectors.toList());

        return new DashboardStatsDTO(totalPatients, upcomingVisits, recentPatientsDTO);
    }
}