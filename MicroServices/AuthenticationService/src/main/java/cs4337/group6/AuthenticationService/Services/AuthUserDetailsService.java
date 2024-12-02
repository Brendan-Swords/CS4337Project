package cs4337.group6.AuthenticationService.Services;

import cs4337.group6.AuthenticationService.Models.AuthUserPrincipal;
import cs4337.group6.AuthenticationService.Models.User;
import cs4337.group6.AuthenticationService.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        if (user.equals(null))
        {
            throw new UsernameNotFoundException("User not found");
        }
        return new AuthUserPrincipal(user);
    }
}
