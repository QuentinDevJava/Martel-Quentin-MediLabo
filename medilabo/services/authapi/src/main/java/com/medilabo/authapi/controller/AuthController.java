package com.medilabo.authapi.controller;

import com.medilabo.authapi.dto.LoginAuth;
import com.medilabo.authapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginAuth request) {
        try {
            log.info("Attempt to authenticate user : {} ", request.getUsername());
            String token = authService.authenticate(request.getUsername(), request.getPassword());
            log.info("User {} authenticated successfully", request.getUsername());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.info("Failed to authenticate user {}", request.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestParam String token) {
        try {
            log.info("Validating token");
            boolean isValid = authService.validateToken(token);
            log.info("Token is valid {}", isValid);
            return isValid ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            log.info("Failed to validate token", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }
}