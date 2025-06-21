package com.medilabo.authapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.authapi.dto.LoginAuth;
import com.medilabo.authapi.services.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginAuth request) {
	log.info("Receive POST /auth/login - Authapi use RestController to authenticate user : {} ",
		request.getUsername());
	try {
	    String token = authService.authenticate(request.getUsername(), request.getPassword());
	    return ResponseEntity.ok(token);
	} catch (Exception e) {
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
	log.info("Receive POST /auth/validate - Authapi use RestController to validate token");

	Boolean tokenIsValid = authService.validateToken(token);

	return ResponseEntity.ok(tokenIsValid);

    }
}