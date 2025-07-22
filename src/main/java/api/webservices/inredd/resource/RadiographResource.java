package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.dto.RadiographDTO;
import api.webservices.inredd.domain.model.dto.RadiographUploadDTO;
import api.webservices.inredd.service.RadiographService;
import api.webservices.inredd.service.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/radiographs")
public class RadiographResource {

    private final RadiographService radiographService;

    public RadiographResource(RadiographService radiographService) {
        this.radiographService = radiographService;
    }

    /**
     * Retrieves a radiograph by its ID.
     *
     * @param radiographId The ID of the radiograph.
     * @return The RadiographDTO.
     */
    @GetMapping("/{radiographId}")
    public ResponseEntity<RadiographDTO> getRadiographById(@PathVariable Long radiographId) {
        try {
            RadiographDTO radiographDTO = new RadiographDTO(radiographService.getRadiographById(radiographId));
            return ResponseEntity.ok(radiographDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the JSON content of the viewer for a specific radiograph.
     *
     * @param radiographId The ID of the radiograph.
     * @return The JSON content as a String.
     */
    @GetMapping("/{radiographId}/viewer-context")
    public ResponseEntity<String> getViewerContextJson(@PathVariable Long radiographId) {
        try {
            String viewerContextJson = radiographService.getViewerContextJson(radiographId);
            return ResponseEntity.ok(viewerContextJson);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Uploads a radiograph image and JSON metadata for a specific visit.
     *
     * @param visitId The ID of the visit.
     * @param patientId The ID of the patient.
     * @param file The binary image file to upload.
     * @param viewerContextJson The JSON metadata for the radiograph viewer context.
     * @return The saved RadiographDTO.
     */
    @PostMapping("/upload")
    public ResponseEntity<RadiographDTO> uploadRadiograph(
            @RequestParam Long visitId,
            @RequestParam Long patientId,
            @RequestParam MultipartFile file,
            @RequestParam String viewerContextJson) {
        try {
            RadiographUploadDTO uploadDTO = new RadiographUploadDTO(visitId, patientId, file, viewerContextJson);
            RadiographDTO radiographDTO = new RadiographDTO(radiographService.uploadRadiograph(uploadDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(radiographDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
