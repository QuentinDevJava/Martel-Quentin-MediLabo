package com.mediLabo.authapi.repository;

import com.mediLabo.authapi.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<User, Long> {
}
