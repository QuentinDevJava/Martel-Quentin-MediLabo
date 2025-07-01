package com.medilabo.evaluationrisqueapi.security.filter;

import com.medilabo.evaluationrisqueapi.security.client.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
@Slf4j
public class TokenAuthorizationFilter extends OncePerRequestFilter {

    private final AuthClient authClient;

    private static final List<String> EXCLUDED_PATHS = List.of("/actuator/health");

    public TokenAuthorizationFilter(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.info("Invalid Authorization header");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String token = authHeader.substring(7);
        try {
            Boolean isTokenValid = authClient.validateToken(token).block();
            log.info("Token is valid: {}", isTokenValid);

            if (isTokenValid == null || !isTokenValid) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Unable to reach the service due to:", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
