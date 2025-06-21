package com.medilabo.gatewayapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
	http.csrf(ServerHttpSecurity.CsrfSpec::disable)

		.authorizeExchange(exchanges -> exchanges

			.pathMatchers("/auth/**", "/actuator/**", "/api/**").permitAll()

			.anyExchange().authenticated());
	return http.build();
    }
}
