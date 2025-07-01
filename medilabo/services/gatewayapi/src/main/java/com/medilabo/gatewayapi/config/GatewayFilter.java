package com.medilabo.gatewayapi.config;

import com.medilabo.gatewayapi.client.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static reactor.core.publisher.Mono.just;

@Component
@Slf4j
public class GatewayFilter implements GlobalFilter {

    private final List<String> ALLOWED_PATHS = List.of("/auth/login", "/auth/validate", "/actuator/health");
    private final AuthClient authClient;

    public GatewayFilter(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("Request URI: {}", request.getURI());

        String path = request.getURI().getPath();
        if (ALLOWED_PATHS.contains(path)) return chain.filter(exchange);

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.info("Please authenticate first");
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            String message = "Please authenticate first";
            DataBuffer dataBuffer = response.bufferFactory().wrap(message.getBytes(UTF_8));
            return response.writeWith(just(dataBuffer));
        }

        log.info("validating token");
        return authClient.validateToken(authHeader.substring(7))
                .flatMap(isValid -> {
                    if (TRUE.equals(isValid)) {
                        log.info("token validated successfully, process request");
                        return chain.filter(exchange);
                    } else {
                        log.info("token validation failed");
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(FORBIDDEN);
                        String message = "Access Denied";
                        DataBuffer dataBuffer = response.bufferFactory().wrap(message.getBytes(UTF_8));
                        return response.writeWith(just(dataBuffer));
                    }
                });
    }

}
