package api.webservices.inredd.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import api.webservices.inredd.domain.model.dto.*;
import api.webservices.inredd.domain.model.dto.CreateAccessRequestDTO;
import api.webservices.inredd.service.AccessRequestService;
import api.webservices.inredd.service.AccessRequestService;

@RestController
@RequestMapping("/access-requests")
@Validated
public class AccessRequestResource {

    @Autowired private AccessRequestService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateAccessRequestDTO dto) {
        service.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateAccessRequestDTO> validate(
        @RequestParam("requestToken") String token) {
        return ResponseEntity.ok(service.validateToken(token));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_SOLUTION_MODERATE_ACCESS_REQUESTS') and #oauth2.hasScope('read')")
    public ResponseEntity<Page<AccessRequestDTO>> list(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable) {
        return ResponseEntity.ok(service.listRequests(search, pageable));
    }
}