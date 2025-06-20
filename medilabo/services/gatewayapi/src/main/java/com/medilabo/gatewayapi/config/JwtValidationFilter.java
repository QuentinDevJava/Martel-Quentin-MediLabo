package com.medilabo.gatewayapi.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.medilabo.gatewayapi.dto.TokenAuth;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtValidationFilter implements WebFilter {

    private final WebClient webClient;

    public JwtValidationFilter(WebClient.Builder builder) {
	this.webClient = builder.build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
	ServerHttpRequest request = exchange.getRequest();
	String path = request.getPath().toString();

	if (path.equals("/auth/login") || path.equals("/auth/token")) {
	    log.info("requete : " + path);
	    return chain.filter(exchange);
	}

	HttpHeaders headers = request.getHeaders();
	String authHeader = headers.getFirst("Authorization");
	String username = headers.getFirst("X-Username");

	if (authHeader != null && authHeader.startsWith("Bearer ") && username != null) {
	    String token = authHeader.substring(7);
	    TokenAuth tokenAuth = new TokenAuth(token, username);
	    try {
		return webClient.post().uri("lb://authapi/auth/token").bodyValue(tokenAuth).retrieve()
			.bodyToMono(Boolean.class).flatMap(valid -> {
			    if (Boolean.TRUE.equals(valid)) {
				return chain.filter(exchange);
			    } else {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			    }
			}).onErrorResume(error -> {
			    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			    return exchange.getResponse().setComplete();
			});

	    } catch (Exception e) {
		log.error("Erreur lors du parsing de l'en-tête Authorization", e);
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	    }

	} else {
	    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	    return exchange.getResponse().setComplete();
	}
    }
}
