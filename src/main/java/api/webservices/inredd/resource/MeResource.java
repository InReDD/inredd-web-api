package api.webservices.inredd.resource;

import api.webservices.inredd.domain.model.dto.MeDTO;
import api.webservices.inredd.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/me")
public class MeResource {

    private final UserService userService;
    private final TokenStore tokenStore;

    public MeResource(UserService userService, TokenStore tokenStore) {
        this.userService = userService;
        this.tokenStore  = tokenStore;
    }

    @Operation(summary = "Dados básicos do usuário logado")
    @GetMapping
    public ResponseEntity<MeDTO> me(Authentication authentication) {
      if (authentication == null || !authentication.isAuthenticated()) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      Object details = authentication.getDetails();
      if (!(details instanceof OAuth2AuthenticationDetails)) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      String tokenValue = ((OAuth2AuthenticationDetails) details).getTokenValue();

      OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
      if (accessToken == null) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      Map<String, Object> info = accessToken.getAdditionalInformation();
      Number userIdNum = (Number) info.get("user_id");
      if (userIdNum == null) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      Long userId = userIdNum.longValue();

      MeDTO dto = userService.getCurrentUserInfo(userId);
      return ResponseEntity.ok(dto);
    }
}
