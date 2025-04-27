
package api.webservices.inredd.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Inredd API").version("1.0"))
            .components(new Components().addSecuritySchemes("oauth2_scheme",
                new SecurityScheme()
                    .type(SecurityScheme.Type.OAUTH2)
                    .flows(new OAuthFlows()
                        .password(new OAuthFlow()
                            .tokenUrl("http://localhost:8881/oauth/token")
                            .scopes(new Scopes()
                                .addString("read", "Leitura dos dados")
                                .addString("write", "Escrita dos dados")
                            )
                        )
                    )
            ))
            .addSecurityItem(new SecurityRequirement().addList("oauth2_scheme"));
    }
}