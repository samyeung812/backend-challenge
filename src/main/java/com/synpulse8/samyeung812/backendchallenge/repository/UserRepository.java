package com.synpulse8.samyeung812.backendchallenge.repository;

import com.synpulse8.samyeung812.backendchallenge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUserID(String userID);
}
