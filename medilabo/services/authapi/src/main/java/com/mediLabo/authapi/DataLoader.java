package com.mediLabo.authapi;

import com.mediLabo.authapi.persistence.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@Slf4j
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;


    @Override
    public void run(String... args) throws Exception {
        log.info("Init users");
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .active(true)
                .build();

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .active(true)
                .build();

        userRepo.saveAll(List.of(admin, user));
        log.info("Saved users");
    }

}
