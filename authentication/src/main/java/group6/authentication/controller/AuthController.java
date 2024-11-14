package group6.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import group6.authentication.service.AuthService;
import group6.authentication.dto.RefreshTokenRequest;
import group6.authentication.exception.InvalidTokenException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            // Call the newly added refreshToken method in AuthService
            String refreshedToken = authService.refreshToken(request.getRefreshToken());
            return new ResponseEntity<>(refreshedToken, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            return new ResponseEntity<>("Invalid or expired refresh token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while refreshing the token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
