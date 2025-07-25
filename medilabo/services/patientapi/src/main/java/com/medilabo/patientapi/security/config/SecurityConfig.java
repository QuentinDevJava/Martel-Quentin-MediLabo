package com.medilabo.patientapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.medilabo.patientapi.security.filters.TokenAuthorizationFilter;

@Configuration
public class SecurityConfig {

    private final TokenAuthorizationFilter tokenFilter;

    public SecurityConfig(TokenAuthorizationFilter tokenFilter) {
	this.tokenFilter = tokenFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
		.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

	return http.build();
    }
}
