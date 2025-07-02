package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.dto.AdvancedSearchResultDTO;
import api.webservices.inredd.domain.model.dto.VisitDTO;
import api.webservices.inredd.domain.model.dto.VisitSearchCriteriaDTO;
import api.webservices.inredd.domain.model.dto.VisitUpdateDTO;
import api.webservices.inredd.service.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * Accepts a JSON object with various filter criteria in the request body.
     *
     * @param searchCriteria The DTO containing all search parameters.
     * @return A ResponseEntity containing the search results and statistics.
     */
    @PostMapping("/search")
    public ResponseEntity<AdvancedSearchResultDTO> searchVisits(@RequestBody VisitSearchCriteriaDTO searchCriteria) {
        AdvancedSearchResultDTO results = visitService.searchVisits(searchCriteria);
        return ResponseEntity.ok(results);
    }

}