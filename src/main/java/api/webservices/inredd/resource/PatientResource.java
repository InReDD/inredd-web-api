package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.dto.PatientCreateDTO;
import api.webservices.inredd.domain.model.dto.PatientDTO;
import api.webservices.inredd.domain.model.dto.VisitCreateDTO;
import api.webservices.inredd.domain.model.dto.VisitDTO;
import api.webservices.inredd.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import api.webservices.inredd.service.VisitService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientResource {

    private final PatientService patientService;
    private final VisitService visitService;

    public PatientResource(PatientService patientService, VisitService visitService) {
        this.patientService = patientService;
        this.visitService = visitService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatientsWithDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientWithDetailsById(id));
    }

    /**
     * POST /api/v1/patients : Create a new patient.
     * The request body must now match the PatientCreateDTO structure.
     */
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientCreateDTO patientCreateDTO) {
        // Service layer accepts the DTO and returns the created entity
        Patient createdPatient = patientService.createPatient(patientCreateDTO);

        PatientDTO responseDTO = new PatientDTO(createdPatient);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPatient.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, 
    @RequestBody PatientCreateDTO patientUpdateDTO) {
        Patient updatedPatient = patientService.updatePatient(id, patientUpdateDTO);
        PatientDTO responseDTO = new PatientDTO(updatedPatient);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{patientId}/visits")
    public ResponseEntity<List<VisitDTO>> getAllVisitsForPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(visitService.getAllVisitsForPatient(patientId));
    }

    @PostMapping("/{patientId}/visits")
    public ResponseEntity<VisitDTO> createVisitForPatient(@PathVariable Long patientId,
        @Validated @RequestBody VisitCreateDTO visitCreateDTO) {
        VisitDTO newVisit = visitService.createVisit(patientId, visitCreateDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newVisit.getId())
                .toUri();

        return ResponseEntity.created(location).body(newVisit);
    }
}