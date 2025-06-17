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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.medilabo.authapi.repository.AuthUserRepository;
import com.medilabo.authapi.services.AuthServicesImpl;
import com.medilabo.authapi.services.JwtServiceImpl;

@ExtendWith(MockitoExtension.class)
class AuthServicesImplTest {

	@Mock
	private AuthUserRepository authUserRepository;

	@Mock
	private JwtServiceImpl jwtService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthServicesImpl authServices;

	@Test
	void autorizedUserReturnTokenWhenCredentialsAreValid() {
		String username = "admin";
		String rawPassword = "admin";
		String encodedPassword = "$2a$10$1234567890";
		String fakeToken = "fake-jwt-token";

		User user = User.builder().username(username).password(encodedPassword).active(true).build();

		when(authUserRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
		when(jwtService.generateToken(any(UserDetails.class))).thenReturn(fakeToken);

		String result = authServices.autorizedUser(username, rawPassword);

		assertEquals(fakeToken, result);
		verify(authUserRepository).findByUsername(username);
		verify(passwordEncoder).matches(rawPassword, encodedPassword);
		verify(jwtService).generateToken(any(UserDetails.class));
	}

	@Test
	void autorizedUserReturnErrorWhenPasswordIsInvalid() {
		String username = "admin";
		String rawPassword = "wrongpassword";

		User user = User.builder().username(username).password("$2a$10$encodedpassword").active(true).build();

		when(authUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(eq(rawPassword), any())).thenReturn(false);

		String result = authServices.autorizedUser(username, rawPassword);

		assertEquals("Invalid username or password", result);
		verify(jwtService, never()).generateToken(any());
	}

	@Test
	void getUserWithUsernameThrowExceptionIfUserNotFound() {
		when(authUserRepository.findByUsername("notfound")).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			authServices.autorizedUser("notfound", "testpassword");
		});
	}
}
