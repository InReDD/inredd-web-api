package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.Patient;
import api.webservices.inredd.domain.model.dto.PatientCreateDTO;
import api.webservices.inredd.domain.model.dto.PatientDTO;
import api.webservices.inredd.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientResource {

    private final PatientService patientService;

    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    // --- GET endpoints remain the same ---
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    /**
     * POST /api/v1/patients : Create a new patient.
     * The request body must now match the PatientCreateDTO structure.
     */
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientCreateDTO patientCreateDTO) {
        // Service layer accepts the DTO and returns the created entity
        Patient createdPatient = patientService.createPatient(patientCreateDTO);

        // We convert the entity to our standard response DTO
        PatientDTO responseDTO = new PatientDTO(createdPatient);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPatient.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    /**
     * PUT /api/v1/patients/{id} : Update an existing patient.
     * The request body must now match the PatientCreateDTO structure.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientCreateDTO patientUpdateDTO) {
        // Service layer accepts the DTO and returns the updated entity
        Patient updatedPatient = patientService.updatePatient(id, patientUpdateDTO);

        // We convert the entity to our standard response DTO
        PatientDTO responseDTO = new PatientDTO(updatedPatient);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * DELETE /api/v1/patients/{id} : Delete a patient by their ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}