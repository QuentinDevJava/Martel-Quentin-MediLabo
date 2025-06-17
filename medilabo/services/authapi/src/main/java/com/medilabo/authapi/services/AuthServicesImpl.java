package com.medilabo.authapi.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medilabo.authapi.User;
import com.medilabo.authapi.repository.AuthUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServicesImpl {

    private final AuthUserRepository authUserRepository;
    private final JwtServiceImpl jwtService;
    private PasswordEncoder passwordEncoder;

    public String autorizedUser(String username, String password) {

	User authUser = getUserByUsername(username);

	if (passwordEncoder.matches(password, authUser.getPassword())) {

	    UserDetails userDetails = new org.springframework.security.core.userdetails.User(authUser.getUsername(),
		    authUser.getPassword(), getAuthorities(authUser));
	    return jwtService.generateToken(userDetails);
	} else {
	    return "Invalid username or password";
	}

    }

    public boolean isTokenValid(String username, String token) {

	User authUser = getUserByUsername(username);

	UserDetails userDetails = new org.springframework.security.core.userdetails.User(authUser.getUsername(),
		authUser.getPassword(), getAuthorities(authUser));

	return jwtService.isTokenValid(token, userDetails);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User authUser) {
	if (authUser.getUsername().equals("admin")) {
	    return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
	} else {
	    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
    }

    private User getUserByUsername(String username) {
	Optional<User> user = authUserRepository.findByUsername(username);
	if (user.isPresent()) {
	    return user.get();
	} else {
	    throw new RuntimeException("User not found");
	}

    }

}
