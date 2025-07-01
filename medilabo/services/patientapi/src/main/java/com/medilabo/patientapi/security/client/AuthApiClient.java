package com.medilabo.patientapi.security.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.OK;
import static reactor.core.publisher.Mono.just;

@Component
@Slf4j
public class AuthApiClient {

    private static final String AUTH_URI = "lb://authapi/auth/validate?token=%s";
    private final WebClient webClient;

    public AuthApiClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public Mono<Boolean> validateToken(String token) {
        String uri = getUri(token);
        return webClient
                .post()
                .uri(uri)
                .exchangeToMono(response -> {
                    log.info("validate token status: {}", response.statusCode());
                    return OK == response.statusCode() ? just(TRUE) : just(FALSE);
                }).timeout(Duration.ofSeconds(10), Mono.error(new TimeoutException("Timeout")));
    }

    private String getUri(String token) {
        return String.format(AUTH_URI, token);
    }
}
