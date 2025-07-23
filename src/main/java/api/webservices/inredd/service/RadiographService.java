package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.Radiograph;
import api.webservices.inredd.domain.model.Visit;
import api.webservices.inredd.domain.model.dto.RadiographUploadDTO;
import api.webservices.inredd.repository.RadiographRepository;
import api.webservices.inredd.repository.VisitRepository;
import api.webservices.inredd.service.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RadiographService {

    private final RadiographRepository radiographRepository;
    private final VisitRepository visitRepository;

    // Corrected constructor name
    public RadiographService(RadiographRepository radiographRepository, VisitRepository visitRepository) {
        this.radiographRepository = radiographRepository;
        this.visitRepository = visitRepository;
    }

    /**
     * Retrieves a radiograph by its ID.
     *
     * @param radiographId The ID of the radiograph.
     * @return The Radiograph entity.
     */
    @Transactional(readOnly = true)
    public Radiograph getRadiographById(Long radiographId) {
        return radiographRepository.findById(radiographId)
                .orElseThrow(() -> new ResourceNotFoundException("Radiograph not found with id: " + radiographId));
    }

    /**
     * Retrieves all radiographs for a specific visit.
     *
     * @param visitId The ID of the visit.
     * @return A list of Radiograph entities.
     */
    @Transactional(readOnly = true)
    public List<Radiograph> getRadiographsByVisitId(Long visitId) {
        if (!visitRepository.existsById(visitId)) {
            throw new ResourceNotFoundException("Visit not found with id: " + visitId);
        }
        return radiographRepository.findAllByVisitId(visitId);
    }

    /**
     * Retrieves the JSON content of the viewer for a specific radiograph.
     *
     * @param radiographId The ID of the radiograph.
     * @return The JSON content as a String.
     */
    @Transactional(readOnly = true)
    public String getViewerContextJson(Long radiographId) {
        Radiograph radiograph = radiographRepository.findById(radiographId)
                .orElseThrow(() -> new ResourceNotFoundException("Radiograph not found with id: " + radiographId));
        return radiograph.getViewerContextJson();
    }

    /**
     * Uploads a radiograph image and JSON metadata for a specific visit.
     *
     * @param visitId The ID of the visit.
     * @param patientId The ID of the patient.
     * @param file The binary image file to upload.
     * @param viewerContextJson The JSON metadata for the radiograph viewer context.
     * @return The saved Radiograph entity.
     */
    @Transactional
    public Radiograph uploadRadiograph(Long visitId, Long patientId, MultipartFile file, String viewerContextJson) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + visitId));

        try {
            Radiograph radiograph = new Radiograph();
            radiograph.setVisit(visit);
            radiograph.setRadiographDate(java.time.LocalDate.now());
            radiograph.setViewerContextJson(viewerContextJson);

            return radiographRepository.save(radiograph);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload radiograph: " + e.getMessage(), e);
        }
    }

    /**
     * Uploads a radiograph using a Data Transfer Object (DTO) for a specific visit.
     *
     * @param uploadDTO The DTO containing radiograph data.
     * @return The saved Radiograph entity.
     */
    @Transactional
    public Radiograph uploadRadiograph(RadiographUploadDTO uploadDTO) {
        Visit visit = visitRepository.findById(uploadDTO.getVisitId())
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + uploadDTO.getVisitId()));

        try {
            Radiograph radiograph = new Radiograph();
            radiograph.setVisit(visit);
            radiograph.setPatientId(uploadDTO.getPatientId());
            radiograph.setRadiographDate(java.time.LocalDate.now());
            radiograph.setViewerContextJson(uploadDTO.getViewerContextJson());

            return radiographRepository.save(radiograph);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload radiograph: " + e.getMessage(), e);
        }
    }
}
