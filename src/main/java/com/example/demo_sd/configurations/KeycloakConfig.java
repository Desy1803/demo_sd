package com.example.demo_sd.configurations;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .clientSecret("ipGpTxezPErLjRTo8hoSBFkETX74coPh")
                .clientId("admin-cli")
                .grantType("client_credentials")
                .realm("spring")
                .serverUrl("http://localhost:8080")
                .build();
    }
}
