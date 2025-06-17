package com.medilabo.authapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.medilabo.authapi.services.JwtServiceImpl;

public class JwtServiceTest {

	private JwtServiceImpl jwtService;

	@BeforeEach
	void setUp() {
		jwtService = new JwtServiceImpl();

		jwtService = new JwtServiceImpl();
		jwtService = injectTestValues(jwtService, "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b",
				3600000);
	}

	@Test
	void generateValidToken() {
		UserDetails userDetails = User.withUsername("testuser").password("testpass").roles("USER").build();

		String token = jwtService.generateToken(userDetails);
		assertNotNull(token);
		assertFalse(token.isEmpty());

		String extractedUsername = jwtService.extractUsername(token);

		assertEquals("testuser", extractedUsername);

		assertTrue(jwtService.isTokenValid(token, userDetails));
	}

	@Test
	void isTokenInvalidWhenExpired() {
		JwtServiceImpl shortLivedJwtService = injectTestValues(new JwtServiceImpl(),
				"3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b", 1);

		UserDetails userDetails = User.withUsername("testuser").password("testpass").roles("USER").build();

		String token = shortLivedJwtService.generateToken(userDetails);

		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		assertFalse(shortLivedJwtService.isTokenValid(token, userDetails));
	}

	@Test
	void isTokenInvalidWithWrongUser() {
		UserDetails realUser = User.withUsername("testuser").password("testpass").roles("USER").build();
		UserDetails otherUser = User.withUsername("anotheruser").password("testpass").roles("USER").build();

		String token = jwtService.generateToken(realUser);

		assertNotNull(token);
		assertFalse(token.isEmpty());

		assertFalse(jwtService.isTokenValid(token, otherUser));
	}

	private JwtServiceImpl injectTestValues(JwtServiceImpl service, String secret, long expiration) {
		try {
			var secretField = JwtServiceImpl.class.getDeclaredField("secretKey");
			secretField.setAccessible(true);
			secretField.set(service, secret);

			var expField = JwtServiceImpl.class.getDeclaredField("jwtExpiration");
			expField.setAccessible(true);
			expField.set(service, expiration);

		} catch (Exception e) {
			throw new RuntimeException("Injection des valeurs de test échouée", e);
		}
		return service;
	}
}
