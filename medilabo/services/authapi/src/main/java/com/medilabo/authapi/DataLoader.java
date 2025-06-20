package com.medilabo.authapi;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.medilabo.authapi.entities.User;
import com.medilabo.authapi.repository.AuthUserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private PasswordEncoder passwordEncoder;
    private AuthUserRepository userRepo;

    @Override
    public void run(String... args) throws Exception {
	log.info("Init users");
	User admin = User.builder().username("admin").password(passwordEncoder.encode("admin")).active(true).build();

	User user = User.builder().username("user").password(passwordEncoder.encode("user")).active(true).build();

	userRepo.saveAll(List.of(admin, user));
	log.info("Saved users");
    }

}
