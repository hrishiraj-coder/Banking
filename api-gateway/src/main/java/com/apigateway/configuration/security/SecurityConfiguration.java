package com.apigateway.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkUri;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        ServerHttpSecurity httpSecurity = http.authorizeExchange(exchanges -> {
            exchanges.pathMatchers("/user/api/v1/bank-user/register").permitAll();

            exchanges.pathMatchers("/actuator/**").permitAll()
                    .pathMatchers("/banking-core/actuator/**").permitAll()
                    .anyExchange().authenticated();
        });

        httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);

        httpSecurity.oauth2ResourceServer(oAuth2ResourceServer -> oAuth2ResourceServer.
                jwt(jwt -> jwt.jwkSetUri(jwkUri)));

        return httpSecurity.build();
    }


}