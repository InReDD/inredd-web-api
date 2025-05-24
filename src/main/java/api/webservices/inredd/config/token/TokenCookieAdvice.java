package api.webservices.inredd.config.token;

import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class TokenCookieAdvice implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getDeclaringClass().getSimpleName().equals("TokenEndpoint");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body,
                                             MethodParameter returnType,
                                             MediaType selectedContentType,
                                             Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                             ServerHttpRequest request,
                                             ServerHttpResponse response) {

        if (body != null && response instanceof ServletServerHttpResponse) {
            HttpServletResponse servletResp =
                ((ServletServerHttpResponse) response).getServletResponse();

            // 1) Cookie “tradicional” (para compatibilidade interna)
            Cookie cookie = new Cookie("ACCESS_TOKEN", body.getValue());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);        // em HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(body.getExpiresIn());
            servletResp.addCookie(cookie);

            // 2) Cabeçalho explícito para SameSite=None
            StringBuilder sb = new StringBuilder();
            sb.append("ACCESS_TOKEN=").append(body.getValue())
              .append("; Max-Age=").append(body.getExpiresIn())
              .append("; Path=/")
              .append("; HttpOnly")
              .append("; Secure")          // apenas em HTTPS
              .append("; SameSite=None");  // <— aqui estamos definindo o SameSite

            servletResp.addHeader("Set-Cookie", sb.toString());
        }

        return body;
    }
}
