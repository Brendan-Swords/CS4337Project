package group6.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group6.authentication.service.AuthService;

@RestController
@RequestMapping("/api/auth") // Base URL for authentication-related routes
public class AuthController {

    private final AuthService authService;

    // Constructor injection of AuthService
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
 // Endpoint for refreshing OAuth token
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody String token) {
        String refreshedToken = authService.refreshToken(token);
        return new ResponseEntity<>(refreshedToken, HttpStatus.OK);
    }
}
