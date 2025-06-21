package com.medilabo.authapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.authapi.entities.AppUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByUsername(String username);
}
