package cs4337.group6.AuthenticationService.Config;

import cs4337.group6.AuthenticationService.Services.JWTService;
import cs4337.group6.AuthenticationService.Utility.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Autowired
    private ReactiveUserDetailsService userDetailsService;

    @Autowired
    private JWTService jwtService;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/public/**", "/Register", "/Login").permitAll() // Allow public endpoints
                        .anyExchange().authenticated() // Protect all other endpoints
                )
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);; // Stateless security context
        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(passwordEncoder);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationWebFilter jwtWebFilter() {
        AuthenticationWebFilter webFilter = new AuthenticationWebFilter(jwtAuthenticationManager());
        webFilter.setServerAuthenticationConverter(jwtAuthenticationConverter());
        webFilter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        return webFilter;
    }

    private ReactiveAuthenticationManager jwtAuthenticationManager() {
        return authentication -> {
            if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
                return Mono.error(new IllegalArgumentException("Unsupported authentication type"));
            }

            String token = (String) authentication.getCredentials();

            // Extract username from the token
            String username = jwtService.ExtractUserName(token);

            // Load user details and validate the token
            return userDetailsService.findByUsername(username)
                    .flatMap(userDetails -> {
                        if (!jwtService.ValidateToken(token, userDetails)) {
                            return Mono.error(new IllegalArgumentException("Invalid JWT Token"));
                        }

                        // Create authenticated token
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, token, userDetails.getAuthorities());
                        authToken.setDetails(userDetails);
                        return Mono.just(authToken);
                    });
        };
    }


    private ServerAuthenticationConverter jwtAuthenticationConverter() {
        return exchange -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.empty(); // No token, skip authentication
            }

            String token = authHeader.substring(7); // Extract the token

            // Create an authentication object with the token as credentials
            return Mono.just(new UsernamePasswordAuthenticationToken(null, token));
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(ReactiveAuthenticationManager reactiveAuthenticationManager) {
        return authentication -> reactiveAuthenticationManager.authenticate(authentication).block(); // Bridge to non-reactive
    }

}
