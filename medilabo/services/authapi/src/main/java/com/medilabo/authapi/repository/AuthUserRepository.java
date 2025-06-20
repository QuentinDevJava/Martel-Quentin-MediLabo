package com.medilabo.authapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.authapi.entities.User;

@Repository
public interface AuthUserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
