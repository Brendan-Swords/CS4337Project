package cs4337.group6.AuthenticationService.Controllers;

import cs4337.group6.AuthenticationService.Models.User;
import cs4337.group6.AuthenticationService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController
{
    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/Register")
    public User Register(@RequestBody User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.Register(user);
    }

    @PostMapping("/Login")
    public String Login(@RequestBody User user)
    {
        String jwtToken = userService.GenerateToken(user);
        if (jwtToken != "N/A")
        {
            return "Successfully logged in as user: " + user.getUsername() + " \n JWT Token: " + jwtToken;
        }
        else
        {
            return "Failed to login, please try again, ensure the password and username is correct. Ensure the account is already registered.";
        }
    }

}