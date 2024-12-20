package cs4337.group6.AuthenticationService.Controllers;

import cs4337.group6.AuthenticationService.Models.User;
import cs4337.group6.AuthenticationService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/Register")
    public Mono<String> register(@RequestBody User user) {
        try
        {
            user.setPassword(encoder.encode(user.getPassword()));
            userService.register(user);
            return Mono.just("Successfully Registered as User: " + user.getUsername());
        }
        catch(Exception e)
        {
            return Mono.just("Failed to Register due to Error: " + e.getMessage());
        }
    }

    @PostMapping("/Login")
    public Mono<String> login(@RequestBody User user) {
        return userService.generateToken(user)
                .map(jwtToken -> {
                    if (!jwtToken.equals("N/A")) {
                        return "Successfully logged in as user: " + user.getUsername() + " \n JWT Token: " + jwtToken;
                    } else {
                        return "Failed to login, please try again. Ensure the username and password are correct and the account is registered.";
                    }
                });
    }
}
