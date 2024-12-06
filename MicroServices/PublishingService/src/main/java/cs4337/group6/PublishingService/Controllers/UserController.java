package cs4337.group6.PublishingService.Controllers;

import cs4337.group6.PublishingService.Models.User;
import cs4337.group6.PublishingService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    /**
     * The method used to add users via posting.
     * @param user
     * @return
     */
    @PostMapping("/AddUser")
    public User AddUser(@RequestBody User user)
    {
        return userService.SaveUser(user);
    }

    /**
     * The method used to remove users via posting.
     * @param user
     */
    @PostMapping("/RemoveUser")
    public void RemoveUser(@RequestBody User user)
    {
        userService.RemoveUser(user);
    }

}
