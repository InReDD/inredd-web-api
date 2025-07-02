package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.dto.VisitDTO;
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
    public ResponseEntity<VisitDTO> updateVisit(@PathVariable Long id, @Validated @RequestBody VisitUpdateDTO visitUpdateDTO) {
        VisitDTO updatedVisit = visitService.updateVisit(id, visitUpdateDTO);
        return ResponseEntity.ok(updatedVisit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }
}