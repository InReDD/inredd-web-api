package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.dto.TermsDTO;
import api.webservices.inredd.domain.model.dto.PrivacyPolicyDTO;
import api.webservices.inredd.service.ParamService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/params")
public class ParamResource {

    private final ParamService service;

    public ParamResource(ParamService service) {
        this.service = service;
    }

    // 1) GET só traz o texto dos Terms of Service, sem contexto de usuário
    @Operation(summary = "Obter Terms of Service")
    @GetMapping("/accept-terms")
    public ResponseEntity<String> getTerms() {
        String content = service.getLatestTermsContent();
        return ResponseEntity.ok(content);
    }

    // 2) PUT aceita os Terms, passando userId como request-param
    @Operation(summary = "Usuário aceita o Terms of Service")
    @PutMapping("/accept-terms")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TERMS_AND_POLICY') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> acceptTerms(@RequestParam("userId") Long userId) {
        service.acceptTerms(userId);
        return ResponseEntity.ok().build();
    }

    // 3) GET só traz o texto da Privacy Policy
    @Operation(summary = "Obter Privacy Policy")
    @GetMapping("/privacy-policy")
    public ResponseEntity<String> getPrivacyPolicy() {
        String content = service.getLatestPrivacyPolicyContent();
        return ResponseEntity.ok(content);
    }

    // 4) PUT aceita a Privacy Policy, passando userId como request-param
    @Operation(summary = "Usuário aceita a Privacy Policy")
    @PutMapping("/privacy-policy")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TERMS_AND_POLICY') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> acceptPrivacyPolicy(@RequestParam("userId") Long userId) {
        service.acceptPrivacyPolicy(userId);
        return ResponseEntity.ok().build();
    }
}