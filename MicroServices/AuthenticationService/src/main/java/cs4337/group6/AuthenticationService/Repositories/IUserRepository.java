package cs4337.group6.AuthenticationService.Repositories;

import cs4337.group6.AuthenticationService.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Self-Explanatory.
 */
public interface IUserRepository extends JpaRepository<User, Integer> //Integer is the datatype of primary key.
{
    User findByUsername(String username);
}
