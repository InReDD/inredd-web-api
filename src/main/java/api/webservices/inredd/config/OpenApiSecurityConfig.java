package api.webservices.inredd.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "oauth2_scheme",
    type = SecuritySchemeType.OAUTH2,
    flows = @io.swagger.v3.oas.annotations.security.OAuthFlows(
        password = @io.swagger.v3.oas.annotations.security.OAuthFlow(
            tokenUrl = "http://localhost:8881/oauth/token",
            scopes = {
                @io.swagger.v3.oas.annotations.security.OAuthScope(name = "read", description = "Leitura"),
                @io.swagger.v3.oas.annotations.security.OAuthScope(name = "write", description = "Escrita")
            }
        )
    )
)
public class OpenApiSecurityConfig {}