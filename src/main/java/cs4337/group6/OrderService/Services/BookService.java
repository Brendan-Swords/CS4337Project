package cs4337.group6.OrderService.Services;

import cs4337.group6.OrderService.Models.Book;
import cs4337.group6.OrderService.Models.User;
import cs4337.group6.Repositories.IBookRepository;
import cs4337.group6.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService
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
    public Book AddBook(Book bookToAdd, Integer publisherId)
    {
        User publisher = userRepo.findById(publisherId).orElseThrow(() -> new RuntimeException("Publisher not found"));

        bookToAdd.setPublisher(publisher);
        return repository.save(bookToAdd);
    }

    public Book RemoveBook(Integer bookId)
    {
        Book book = repository.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not Found"));
        repository.delete(book);
        return book;
    }
}
