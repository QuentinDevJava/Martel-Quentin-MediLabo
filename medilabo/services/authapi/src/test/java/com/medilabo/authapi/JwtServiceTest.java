package com.medilabo.authapi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.medilabo.authapi.services.JwtService;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
	jwtService = new JwtService(userDetailsService);
	jwtService = injectTestValues(jwtService, "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b",
		3600000);
    }

    @Test
    void generateValidToken() {

	UserDetails userDetails = User.withUsername("testuser").password("testpass").roles("USER").build();

	when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

	String token = jwtService.generateToken(userDetails);

	assertNotNull(token);
	assertFalse(token.isEmpty());

	assertTrue(jwtService.validateToken(token));
    }

    @Test
    void isTokenInvalidWhenExpired() {
	JwtService shortLivedJwtService = injectTestValues(new JwtService(userDetailsService),
		"3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b", 1);

	UserDetails userDetails = User.withUsername("testuser").password("testpass").roles("USER").build();

	String token = shortLivedJwtService.generateToken(userDetails);

	try {
	    Thread.sleep(5);
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	}

	assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> shortLivedJwtService.validateToken(token));
    }

    private JwtService injectTestValues(JwtService service, String secret, long expiration) {
	try {
	    var secretField = JwtService.class.getDeclaredField("secretKey");
	    secretField.setAccessible(true);
	    secretField.set(service, secret);

	    var expField = JwtService.class.getDeclaredField("jwtExpiration");
	    expField.setAccessible(true);
	    expField.set(service, expiration);

	} catch (Exception e) {
	    throw new RuntimeException("Test value injection failed", e);
	}
	return service;
    }
}
