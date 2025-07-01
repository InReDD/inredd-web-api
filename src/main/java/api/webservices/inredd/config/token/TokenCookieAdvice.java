package api.webservices.inredd.config.token;

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

    @SuppressWarnings("null")
    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getDeclaringClass().getSimpleName().equals("TokenEndpoint");
    }

    @SuppressWarnings("null")
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

            Cookie cookie = new Cookie("ACCESS_TOKEN", body.getValue());
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setDomain("localhost");
            cookie.setPath("/");
            cookie.setMaxAge(body.getExpiresIn());
            servletResp.addCookie(cookie);

            StringBuilder sb = new StringBuilder();
            sb.append("ACCESS_TOKEN=").append(body.getValue())
              .append("; Domain=localhost")
              .append("; Max-Age=").append(body.getExpiresIn())
              .append("; Path=/")
              .append("; SameSite=None");  // <— aqui estamos definindo o SameSite

            servletResp.addHeader("Set-Cookie", sb.toString());
        }

        return body;
    }
}
