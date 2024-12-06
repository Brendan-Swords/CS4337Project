package cs4337.group6.AuthenticationService.Utility;

import cs4337.group6.AuthenticationService.Services.AuthUserDetailsService;
import cs4337.group6.AuthenticationService.Services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthUserDetailsService userDetailsService; // Use direct injection for clarity

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If no valid Authorization header, continue the filter chain
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7); // Extract the token
        String username = jwtService.ExtractUserName(token);

        if (username == null) {
            // If username is null, continue the chain
            return chain.filter(exchange);
        }

        return userDetailsService.findByUsername(username)
                .flatMap(userDetails -> {
                    if (jwtService.ValidateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        SecurityContext securityContext = new SecurityContextImpl(authentication);

                        // Attach security context and proceed with the chain
                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
                    }
                    // If token is invalid, continue the chain without authentication
                    return chain.filter(exchange);
                })
                .onErrorResume(e -> {
                    // Handle errors (e.g., UsernameNotFoundException)
                    return chain.filter(exchange);
                });
    }
}
