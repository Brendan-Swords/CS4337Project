package cs4337.group6.AuthenticationService.Services;

import cs4337.group6.AuthenticationService.Models.User;
import cs4337.group6.AuthenticationService.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    public User Register(User user)
    {
        return userRepository.save(user);
    }

    public List<User> GetAllUsers()
    {
        return userRepository.findAll();
    }

    public String GenerateToken(User user)
    {
        if(IsVerified(user))
        {
            return jwtService.GenerateToken(user.getUsername());
        }
        else
            return "N/A";
    }

    private boolean IsVerified(User user)
    {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        return authentication.isAuthenticated();
    }
}
