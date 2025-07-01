package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.dto.*;
import api.webservices.inredd.service.ParamService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/params")
public class ParamResource {

    private final ParamService service;
    public ParamResource(ParamService service, TokenStore tokenStore) {
        this.service = service;
    }

    // 1) GET só traz o texto dos Terms of Service, sem contexto de usuário
    @Operation(summary = "Obter Terms of Service")
    @GetMapping("/accept-terms")
    public ResponseEntity<String> getTerms() {
        String content = service.getLatestTermsContent();
        return ResponseEntity.ok(content);
    }

    /**
     * PUT /params/accept-terms
     * O usuário logado aceita os Terms of Service.
     */
    @Operation(summary = "Usuário aceita o Terms of Service")
    @PutMapping("/accept-terms")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TERMS_AND_POLICY') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> acceptTerms(Authentication authentication) {
        Long userId = service.extractUserId(authentication);
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

    /**
     * PUT /params/privacy-policy
     * O usuário logado aceita a Privacy Policy.
     */
    @Operation(summary = "Usuário aceita a Privacy Policy")
    @PutMapping("/privacy-policy")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TERMS_AND_POLICY') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> acceptPrivacyPolicy(Authentication authentication) {
        Long userId = service.extractUserId(authentication);
        service.acceptPrivacyPolicy(userId);
        return ResponseEntity.ok().build();

    }

    /**
     * PUT /params/refuse-terms
     * O usuário logado recusa os Terms of Service.
     */
    @Operation(summary = "Usuário recusa o Terms of Service")
    @PutMapping("/refuse-terms")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TERMS_AND_POLICY') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> refuseTerms(Authentication authentication) {
        Long userId = service.extractUserId(authentication);
        service.refuseTerms(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * PUT /params/refuse-privacy-policy
     * O usuário logado recusa a Privacy Policy.
     */
    @Operation(summary = "Usuário recusa a Privacy Policy")
    @PutMapping("/refuse-privacy-policy")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TERMS_AND_POLICY') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> refusePrivacyPolicy(Authentication authentication) {
        Long userId = service.extractUserId(authentication);
        service.refusePrivacyPolicy(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar o texto dos Terms of Service")
    @PutMapping("/accept-terms/content")
    @PreAuthorize("hasAuthority('ROLE_EDIT_ACCEPT_TERMS') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> updateTermsText(@RequestBody PolicyTextDTO dto) {
        service.updateTermsText(dto.getContent());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar o texto da Privacy Policy")
    @PutMapping("/privacy-policy/content")
    @PreAuthorize("hasAuthority('ROLE_EDIT_PRIVACY_POLICY') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> updatePrivacyText(@RequestBody PolicyTextDTO dto) {
        service.updatePrivacyText(dto.getContent());
        return ResponseEntity.ok().build();
    }
    
}