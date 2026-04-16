package com.myseals.repository;

import com.myseals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByAuth0UserId(String auth0UserId);
    Optional<User> findByEmail(String email);
}
