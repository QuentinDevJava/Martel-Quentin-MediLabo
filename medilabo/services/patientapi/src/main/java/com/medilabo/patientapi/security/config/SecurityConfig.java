package com.medilabo.patientapi.security.config;

import com.medilabo.patientapi.security.filters.TokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final TokenValidatorFilter tokenFilter;

    public SecurityConfig(TokenValidatorFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
