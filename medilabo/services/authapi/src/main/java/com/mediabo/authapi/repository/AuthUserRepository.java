package com.mediabo.authapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediabo.authapi.User;

@Repository
public interface AuthUserRepository extends JpaRepository<User, Long> {
}
