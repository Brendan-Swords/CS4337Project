package cs4337.group6.PublishingService.Services;

import cs4337.group6.PublishingService.Models.Book;
import cs4337.group6.PublishingService.Models.User;
import cs4337.group6.PublishingService.Repositories.IBookRepository;
import cs4337.group6.PublishingService.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublishingService
{
    @Autowired
    private IBookRepository repository;
    @Autowired
    private IUserRepository userRepo;

    /**
     * Adds a book to the database -> used for publishing.
     * @param bookToAdd The book to add.
     * @return The book if added or null if not.
     */
    public Book PublishBook(Book bookToAdd, Integer publisherId)
    {
        User publisher = userRepo.findById(publisherId).orElseThrow(() -> new RuntimeException("Publisher not found"));

        bookToAdd.setPublisher(publisher);
        return repository.save(bookToAdd);
    }

    public void RemoveBook(Integer bookId)
    {
        Book book = repository.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not Found"));
        repository.delete(book);
    }

    public List<Book> GetAllPublishedBooks()
    {
        return repository.findAll();
    }
}
