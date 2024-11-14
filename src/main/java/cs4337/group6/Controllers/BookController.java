package cs4337.group6.Controllers;

import cs4337.group6.Models.Book;
import cs4337.group6.Services.BookService;
import cs4337.group6.Utility.PostmanResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController
{
    @Autowired
    private BookService bookService;

    /**
     * The mapping for publishing books via postman.
     * @param book The book to be published.
     * @return The book if published or null.
     */
    @PostMapping(value = "/{publisherId}/PublishBook", produces = "application/json")
    public ResponseEntity<PostmanResponseMessage<Book>> AddBook(@RequestBody Book book, @PathVariable Integer publisherId)
    {
        try
        {
            Book publishedBook = bookService.AddBook(book, publisherId);
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Book created successfully", HttpStatus.OK.value(), publishedBook));
        }
        catch(Exception e)
        {
            System.err.println("Could not add book due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Failed to create book: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @PostMapping("/RemoveBook/{bookId}")
    public ResponseEntity<PostmanResponseMessage<Book>> RemoveBook(@PathVariable Integer bookId)
    {
        try
        {
            Book book = bookService.RemoveBook(bookId);
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Book removed successfully", HttpStatus.OK.value(), book));
        }
        catch(Exception e)
        {
            System.err.println("Could not remove book(ID: " + bookId + ") due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Failed to remove book: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

}
