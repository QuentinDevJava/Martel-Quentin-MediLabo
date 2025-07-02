package com.medilabo.authapi;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.authapi.dto.LoginAuth;
import com.medilabo.authapi.services.AuthService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginWhenCredentialsAreValid() throws Exception {
	LoginAuth request = LoginAuth.builder().username("testuser").password("password").build();

	when(authService.authenticate("testuser", "password")).thenReturn("fake-jwt-token");

	mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
		.andExpect(content().string("fake-jwt-token"));
    }

    @Test
    void loginWhenCredentialsAreInvalid() throws Exception {
	LoginAuth request = LoginAuth.builder().username("testuser").password("wrongpassword").build();

	when(authService.authenticate("testuser", "wrongpassword"))
		.thenThrow(new RuntimeException("Invalid credentials"));

	mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(request))).andExpect(status().isUnauthorized())
		.andExpect(content().string("Invalid credentials"));
    }

    @Test
    void validateTokenWhenTokenIsValid() throws Exception {
	when(authService.validateToken("valid-token")).thenReturn(true);

	mockMvc.perform(post("/auth/validate").param("token", "valid-token")).andExpect(status().isOk());
    }

    @Test
    void validateTokenWhenTokenIsInvalid() throws Exception {
	when(authService.validateToken("invalid-token")).thenReturn(false);

	mockMvc.perform(post("/auth/validate").param("token", "invalid-token")).andExpect(status().isForbidden());
    }
}
