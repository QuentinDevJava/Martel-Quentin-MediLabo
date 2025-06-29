package com.medilabo.authapi.services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medilabo.authapi.entities.AppUser;
import com.medilabo.authapi.repository.AuthUserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(String username, String password) {
	AppUser appUser = getUserByUsername(username);

	if (passwordMatches(password, appUser)) {
	    UserDetails userDetails = new User(appUser.getUsername(), appUser.getPassword(), getAuthorities(appUser));
	    return jwtService.generateToken(userDetails);
	} else {
	    throw new BadCredentialsException(String.format("Cannot authenticate user: %s", username));
	}

    }

    public boolean validateToken(String token) {
	log.info("Validating token");

	boolean isValidated = jwtService.validateToken(token);
	log.info("Token validated {}", isValidated);

	return isValidated;
    }

    private boolean passwordMatches(String password, AppUser user) {
	return passwordEncoder.matches(password, user.getPassword());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(AppUser authUser) {
	if (authUser.getUsername().equals("admin")) {
	    return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
	} else {
	    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
    }

    private AppUser getUserByUsername(String username) {
	return authUserRepository.findByUsername(username)
		.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

}
