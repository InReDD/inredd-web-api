package api.webservices.inredd.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService   userDetailsService;
    private final TokenStore           tokenStore;
    private final JwtAccessTokenConverter accessTokenConverter;
    private final TokenEnhancer        tokenEnhancer;

    public AuthorizationServerConfig(
        AuthenticationManager authenticationManager,
        UserDetailsService   userDetailsService,
        TokenStore           tokenStore,
        JwtAccessTokenConverter accessTokenConverter,
        TokenEnhancer        tokenEnhancer
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService   = userDetailsService;
        this.tokenStore           = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
        this.tokenEnhancer        = tokenEnhancer;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("client")   .secret("$2a$10$f.yKGdqWDD2pSbVHxzXrh.3umI6pPRQ/D30yIMIrT4aHaZO.xBmTK")  .scopes("read","write")
              .authorizedGrantTypes("password","refresh_token")
              .accessTokenValiditySeconds(3600)
              .refreshTokenValiditySeconds(3600 * 24)
        .and()
            .withClient("mobile")   .secret("$2a$10$WrK54bx3KDc60I5XwVdW..ltgfz7s1l/uHEMtu9z0K9ujnS4ljfLa") .scopes("read")
              .authorizedGrantTypes("password","refresh_token")
              .accessTokenValiditySeconds(3600)
              .refreshTokenValiditySeconds(3600 * 24);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter));

        endpoints
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
            .tokenStore(tokenStore)
            .tokenEnhancer(chain)
            .reuseRefreshTokens(false);
    }
}
