package com.medilabo.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import com.medilabo.ui.dto.LoginAuth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthUiController {

    private final RestClient restClient;

    @Value("${auth.api.url}")
    private String authApiUrl;

    @GetMapping("/login")
    public String showLoginForm() {
	return "login";
    }

    @PostMapping("/login")
    public String authenticationAuthApi(@RequestParam String username, @RequestParam String password,
	    HttpSession session, Model model) {

	LoginAuth loginRequest = LoginAuth.builder().username(username).password(password).build();

	try {
	    String token = restClient.post()

		    .uri(authApiUrl + "login")

		    .body(loginRequest)

		    .retrieve()

		    .body(String.class);

	    session.setAttribute("token", token);
	    session.setAttribute("username", username);

	    return "redirect:/patients";

	} catch (Exception e) {
	    model.addAttribute("loginError", "Nom d’utilisateur ou mot de passe invalide");
	    return "login";
	}
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
	session.invalidate();
	return "redirect:/auth/login";
    }

}
