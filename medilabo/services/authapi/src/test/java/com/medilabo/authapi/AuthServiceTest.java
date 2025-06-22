package com.medilabo.authapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.medilabo.authapi.entities.AppUser;
import com.medilabo.authapi.repository.AuthUserRepository;
import com.medilabo.authapi.services.AuthService;
import com.medilabo.authapi.services.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthUserRepository authUserRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authServices;

    @Test
    void authenticateReturnTokenWhenCredentialsAreValid() {
	String username = "admin";
	String rawPassword = "admin";
	String encodedPassword = "$2a$10$1234567890";
	String fakeToken = "fake-jwt-token";

	AppUser user = AppUser.builder().username(username).password(encodedPassword).active(true).build();

	when(authUserRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
	when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
	when(jwtService.generateToken(any(UserDetails.class))).thenReturn(fakeToken);

	String result = authServices.authenticate(username, rawPassword);

	assertEquals(fakeToken, result);
	verify(authUserRepository).findByUsername(username);
	verify(passwordEncoder).matches(rawPassword, encodedPassword);
	verify(jwtService).generateToken(any(UserDetails.class));
    }

    @Test
    void authenticateReturnErrorWhenPasswordIsInvalid() {
	String username = "admin";
	String rawPassword = "wrongpassword";

	AppUser user = AppUser.builder().username(username).password("$2a$10$encodedpassword").active(true).build();

	when(authUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
	when(passwordEncoder.matches(eq(rawPassword), any())).thenReturn(false);

	assertThrows(BadCredentialsException.class, () -> {
	    authServices.authenticate(username, rawPassword);
	});

	verify(jwtService, never()).generateToken(any());
    }

    @Test
    void getUserWithUsernameThrowExceptionIfUserNotFound() {
	when(authUserRepository.findByUsername("notfound")).thenReturn(Optional.empty());

	assertThrows(RuntimeException.class, () -> {
	    authServices.authenticate("notfound", "testpassword");
	});
    }
}
