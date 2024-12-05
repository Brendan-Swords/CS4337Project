package cs4337.group6.AuthenticationService.Controllers;

import cs4337.group6.AuthenticationService.Models.User;
import cs4337.group6.AuthenticationService.Services.UserService;
import cs4337.group6.AuthenticationService.Utility.PostmanResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthenticationController
{
    @Autowired
    private UserService userService;

    @GetMapping("/User")
    public Principal User(Principal principal)
    {
        System.out.println("Username: " + principal.getName());
        return principal;
    }

    @GetMapping(value = "/Users")
    public Mono<ResponseEntity<PostmanResponseMessage<List<User>>>> GetAllUsers() {
        return userService.getAllUsers()
                .collectList() // Collect Flux<User> into a List<User>
                .map(users -> ResponseEntity.ok(
                        new PostmanResponseMessage<>("Got a list of Users successfully", HttpStatus.OK.value(), users)))
                .onErrorResume(e -> {
                    System.err.println("Could not get the list of Users due to exception: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new PostmanResponseMessage<>(
                                    "Could not get a list of Users due to exception: " + e.getMessage(),
                                    HttpStatus.BAD_REQUEST.value(),
                                    null)));
                });
        }
}

