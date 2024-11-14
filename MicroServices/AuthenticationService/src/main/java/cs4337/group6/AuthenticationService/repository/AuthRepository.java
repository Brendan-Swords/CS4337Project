package cs4337.group6.AuthenticationService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cs4337.group6.AuthenticationService.model.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    // Custom query method to find a user by their username
    Optional<User> findByUsername(String username);

    // Custom query method to find a user by their email
    Optional<User> findByEmail(String email);

    // Optional: You can add more methods to search users by roles, etc.
}
