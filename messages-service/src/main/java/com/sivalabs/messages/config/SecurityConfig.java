package com.sivalabs.messages.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(c ->
                c
                    .requestMatchers(HttpMethod.GET, "/api/messages").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/messages/archive").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(CorsConfigurer::disable)
            .csrf(CsrfConfigurer::disable)
            .oauth2ResourceServer(oauth2 ->
                //oauth2.jwt(Customizer.withDefaults())
                oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter()))
            );
        return http.build();
    }
}
