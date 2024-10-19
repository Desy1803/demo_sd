package com.example.demo_sd.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web)->{
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/public/**",
                    "/api/users/security/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.GET,
                    "/public/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.DELETE,
                    "/public/**",
                    "/api/users/security/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.PUT,
                    "/public/**",
                    "/api/users/security/**"
            ).requestMatchers("/v3/api-docs/**", "/configuration/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**");
        };
    }

}
