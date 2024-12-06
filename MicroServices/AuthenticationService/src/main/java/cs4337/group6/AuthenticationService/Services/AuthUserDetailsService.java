package cs4337.group6.AuthenticationService.Services;

import cs4337.group6.AuthenticationService.Models.AuthUserPrincipal;
import cs4337.group6.AuthenticationService.Models.User;
import cs4337.group6.AuthenticationService.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.defer(() -> {
            // Retrieve user from repository
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return Mono.error(new UsernameNotFoundException("User not found"));
            }
            // Return a Mono of UserDetails
            return Mono.just(new AuthUserPrincipal(user));
        });
    }
}
