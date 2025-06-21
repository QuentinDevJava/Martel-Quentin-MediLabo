package com.medilabo.gatewayapi.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GatewayFilter implements WebFilter {

    private final WebClient webClient;

    public GatewayFilter(WebClient.Builder builder) {
	this.webClient = builder.build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
	ServerHttpRequest request = exchange.getRequest();
	String path = request.getPath().toString();
	log.info("path {}", path);

	if (path.equals("/auth/login") || path.equals("/auth/validate") || path.equals("/actuator/health")) {
	    return chain.filter(exchange);
	}

	HttpHeaders headers = request.getHeaders();
	String authHeader = headers.getFirst("Authorization");

	if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
	    return exchange.getResponse().setComplete();
	}

	String token = authHeader.substring(7);
	return callAuthApi(exchange, chain, token);
    }

    private Mono<Void> callAuthApi(ServerWebExchange exchange, WebFilterChain chain, String token) {
	return webClient.post().uri("lb://authapi/auth/validate?token=" + token) // appel via service discovery
		.retrieve().bodyToMono(Boolean.class).flatMap(valid -> {
		    if (Boolean.TRUE.equals(valid)) {
			return chain.filter(exchange);
		    } else {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		    }
		}).onErrorResume(error -> {
		    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		    return exchange.getResponse().setComplete();
		});
    }

}
