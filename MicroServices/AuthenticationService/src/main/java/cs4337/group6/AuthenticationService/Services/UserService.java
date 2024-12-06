package cs4337.group6.AuthenticationService.Services;

import cs4337.group6.AuthenticationService.Models.User;
import cs4337.group6.AuthenticationService.Repositories.IUserRepository;
import cs4337.group6.AuthenticationService.Utility.PostmanResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ReactiveAuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    // Register a new user
    public Mono<User> register(User user) {
        return Mono.just(userRepository.save(user)); // Save operation wrapped in a Mono
    }

    // Get all users as a reactive stream
    public Flux<User> getAllUsers() {
        return Flux.fromIterable(userRepository.findAll());
    }

    // Generate a JWT token for a verified user
    public Mono<String> generateToken(User user) {
        return isVerified(user)
                .flatMap(isAuthenticated -> {
                    if (isAuthenticated) {

                        return Mono.just(jwtService.GenerateToken(user.getUsername()));
                    } else {
                        return Mono.just("N/A");
                    }
                });
    }

    // Check if a user is verified
    private Mono<Boolean> isVerified(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        return authManager.authenticate(authenticationToken) // Authenticate reactively
                .map(Authentication::isAuthenticated)
                .onErrorResume(e -> Mono.just(false)); // Handle authentication failures gracefully
    }
}
