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
        log.info("Authenticate user : {}", request.getUsername());
        try {
            String token = authService.authenticate(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
        log.info("validating token");
        Boolean tokenIsValid = authService.validateToken(token);

        return ResponseEntity.ok(tokenIsValid);

    }
}