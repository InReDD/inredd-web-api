package api.webservices.inredd.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import api.webservices.inredd.domain.model.dto.*;
import api.webservices.inredd.service.AccessRequestService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/access-requests")
@Validated
public class RequestResource {

    @Autowired private AccessRequestService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateAccessRequestDTO dto) {
        service.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateAccessRequestDTO> validate(
        @RequestParam(value = "requestToken", required = false) String requestToken,
        @RequestParam(value = "inviteToken", required = false) String inviteToken) {

        if (requestToken != null) {
            return ResponseEntity.ok(service.validateRequestToken(requestToken));
        } else if (inviteToken != null) {
            return ResponseEntity.ok(service.validateInviteToken(inviteToken));
        } else {
            throw new IllegalArgumentException("Parâmetro requestToken ou inviteToken deve ser informado.");
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_SOLUTION_MODERATE_ACCESS_REQUESTS') and #oauth2.hasScope('read')")
    public ResponseEntity<Page<AccessRequestDTO>> list(
            @RequestParam(value = "search",    required = false) String  search,
            @RequestParam(value = "completed", required = false) Boolean completed,
            Pageable pageable) {

        Page<AccessRequestDTO> page = service.listRequests(search, completed, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_SOLUTION_MODERATE_ACCESS_REQUESTS') and #oauth2.hasScope('read')")
    public ResponseEntity<AccessRequestDetailDTO> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAccessRequestDetailById(id));
    }

    @Operation(summary = "Aprovar solicitação de acesso")
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('ROLE_SOLUTION_MODERATE_ACCESS_REQUESTS') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> approve(@PathVariable Long id) {
        service.approveRequest(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Rejeitar solicitação de acesso")
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('ROLE_SOLUTION_MODERATE_ACCESS_REQUESTS') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> reject(
            @PathVariable Long id,
            @Valid @RequestBody RejectAccessRequestDTO dto) {

        service.rejectRequest(id, dto.getReason());
        return ResponseEntity.noContent().build();
    }
}