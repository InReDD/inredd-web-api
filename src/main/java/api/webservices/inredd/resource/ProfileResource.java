package api.webservices.inredd.resource;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import api.webservices.inredd.domain.model.dto.ProfileDTO;
import api.webservices.inredd.domain.model.dto.ProfileUpdateDTO;
import api.webservices.inredd.service.UserService;

@RestController
@RequestMapping("/profile")
public class ProfileResource {

    private final UserService userService;
    private final TokenStore tokenStore;

    @Autowired
    public ProfileResource(UserService userService, TokenStore tokenStore) {
        this.userService = userService;
        this.tokenStore  = tokenStore;
    }

    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long userId = extractUserId(authentication);
        ProfileDTO dto = userService.getProfile(userId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<ProfileDTO> updateProfile(
            Authentication authentication,
            @Valid @RequestBody ProfileUpdateDTO upd
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long userId = extractUserId(authentication);
        ProfileDTO updated = userService.updateProfile(userId, upd);
        return ResponseEntity.ok(updated);
    }

    private Long extractUserId(Authentication authentication) {
        Object details = authentication.getDetails();
        if (!(details instanceof OAuth2AuthenticationDetails)) {
            throw new IllegalStateException("Invalid authentication details");
        }
        String tokenValue = ((OAuth2AuthenticationDetails) details).getTokenValue();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        @SuppressWarnings("unchecked")
        Map<String, Object> info = accessToken.getAdditionalInformation();
        Number userIdNum = (Number) info.get("user_id");
        if (userIdNum == null) {
            throw new IllegalStateException("user_id not found in token");
        }
        return userIdNum.longValue();
    }
}
