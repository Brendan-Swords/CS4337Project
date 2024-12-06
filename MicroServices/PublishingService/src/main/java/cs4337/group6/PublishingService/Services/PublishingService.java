package cs4337.group6.PublishingService.Services;

import cs4337.group6.PublishingService.Models.Book;
import cs4337.group6.PublishingService.Models.User;
import cs4337.group6.PublishingService.Repositories.IBookRepository;
import cs4337.group6.PublishingService.Repositories.IUserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public Book PublishBook(Book bookToAdd, String username)
    {
        User publisher = userRepo.findByUsername(username);

        if (publisher == null) {
        throw new IllegalArgumentException("No user found with username: " + username);
        }
        //System.out.println(publisher);
        bookToAdd.setPublisher(publisher);

        return repository.save(bookToAdd);
    }

    public void RemoveBook(Integer bookId, String username) throws Exception {
        User currentUser = userRepo.findByUsername(username);
        Book book = repository.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not Found"));
        if(book.getPublisher().equals(currentUser))
        {
            repository.delete(book);
        }
        else
        {
            throw new Exception("You are not the publisher of this book!");
        }
    }

    public List<Book> GetAllPublishedBooks()
    {
        List<Book> books = repository.findAll();
        books.forEach(book -> Hibernate.initialize(book.getBuyers()));
        books.forEach(book -> {
            book.setBuyers(Collections.unmodifiableSet(book.getBuyers()));
        });

        return Collections.unmodifiableList(books);
    }
}
