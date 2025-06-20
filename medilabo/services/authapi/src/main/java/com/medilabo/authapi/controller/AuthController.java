package com.medilabo.authapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.authapi.dto.LoginAuth;
import com.medilabo.authapi.dto.TokenAuth;
import com.medilabo.authapi.services.AuthServicesImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServicesImpl authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginAuth request) {
	String token = authService.autorizedUser(request.getUsername(), request.getPassword());

	if (token.equals("Invalid username or password")) {
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<Boolean> tokenValidation(@RequestBody TokenAuth request) {

	Boolean tokenIsValid = authService.isTokenValid(request.getUsername(), request.getToken());

	return ResponseEntity.ok(tokenIsValid);

    }
}