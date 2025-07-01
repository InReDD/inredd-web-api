package api.webservices.inredd.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tokens")
@Tag(name = "Tokens", description = "Operações relacionadas à gestão de tokens de autenticação")
public class TokenResource {

	@Operation(
        summary = "Revogar o refresh token",
        description = "Revoga o refresh token removendo o cookie `refreshToken` do cliente. Após essa operação, o cliente não poderá mais usar o refresh token para obter um novo access token. A operação retorna um status HTTP 204 (No Content) em caso de sucesso. Esse endpoint é usado para realizar logout do usuário, removendo o token de autenticação."
    )
	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(false); // TODO: Em producao sera true
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
}

