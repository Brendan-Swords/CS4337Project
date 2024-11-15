package cs4337.group6.PublishingService.Services;

import cs4337.group6.PublishingService.Models.User;
import cs4337.group6.PublishingService.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private IUserRepository repository;

    /**
     * Adds a user to the database.
     * @param userToSave The user to be added.
     * @return The user who is added or null if failed.
     */
    public User SaveUser(User userToSave)
    {
        try
        {
            return repository.save(userToSave);
        }
        catch (Exception e)
        {
            System.err.println("Could not save user(" + userToSave.GetId() + ") due to exception: "  + e.getMessage());
            return null;
        }
    }

    /**
     * Removes the user from database.
     * @param userToDelete the user to be removed.
     * @return true or false -> did it happen?
     */
    public boolean RemoveUser(User userToDelete)
    {
        try
        {
            repository.delete(userToDelete);
            return true;
        }
        catch (Exception e)
        {
            System.err.println("Could not remove user(" + userToDelete.GetId() + ") due to exception: "  + e.getMessage());
            return false;
        }
    }
}
