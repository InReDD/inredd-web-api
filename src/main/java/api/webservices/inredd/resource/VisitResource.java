package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.dto.AdvancedSearchResultDTO;
import api.webservices.inredd.domain.model.dto.VisitCreateDTO;
import api.webservices.inredd.domain.model.dto.VisitDTO;
import api.webservices.inredd.domain.model.dto.VisitSearchCriteriaDTO;
import api.webservices.inredd.domain.model.dto.VisitUpdateDTO;
import api.webservices.inredd.service.VisitService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/visits")
public class VisitResource {

    private final VisitService visitService;

    public VisitResource(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> getVisitById(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getVisitById(id));
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<VisitDTO> createVisit(
            @RequestParam Long patientId,
            @RequestPart(value = "visitCreateDTO", required = false) @Validated VisitCreateDTO visitCreateDTO,
            @RequestPart(value = "radiographImage", required = false) MultipartFile radiographImage) {
        VisitDTO createdVisit = visitService.createVisit(patientId, visitCreateDTO, radiographImage);
        return ResponseEntity.status(201).body(createdVisit); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitDTO> updateVisit(@PathVariable Long id,
            @Validated @RequestBody VisitUpdateDTO visitUpdateDTO) {
        VisitDTO updatedVisit = visitService.updateVisit(id, visitUpdateDTO);
        return ResponseEntity.ok(updatedVisit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles advanced search requests for visits.
     */
    @PostMapping("/search")
    public ResponseEntity<AdvancedSearchResultDTO> searchVisits(@RequestBody VisitSearchCriteriaDTO searchCriteria) {
        AdvancedSearchResultDTO results = visitService.searchVisits(searchCriteria);
        return ResponseEntity.ok(results);
    }
}