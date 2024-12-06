package cs4337.group6.PublishingService.Controllers;

import cs4337.group6.PublishingService.Models.Book;
import cs4337.group6.PublishingService.Services.PublishingService;
import cs4337.group6.PublishingService.Utility.PostmanResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PublishController
{
    @Autowired
    private PublishingService bookService;
    @Autowired
    private RestTemplate restTemplate;


    /**
     * The mapping for publishing books via postman.
     * @param book The book to be published.
     * @return The book if published or null.
     */
    @PostMapping(value = "/PublishBook", produces = "application/json")
    public ResponseEntity<PostmanResponseMessage<Book>> AddBook(@RequestBody Book book)
    {
        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Internal-Request", "true");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://authentication-service:8080/auth/Username",
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String username = response.getBody();
            System.out.println(username);

            Book publishedBook = bookService.PublishBook(book, username);
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Book published successfully", HttpStatus.OK.value(), publishedBook));
        }
        catch(Exception e)
        {
            System.err.println("Could not publish book due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Failed to publish book: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @PostMapping(value = "/DelistBook/{bookId}", produces = "application/json")
    public ResponseEntity<PostmanResponseMessage<Book>> RemoveBook(@PathVariable Integer bookId)
    {
        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Internal-Request", "true");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://authentication-service:8080/auth/Username",
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String username = response.getBody();

            bookService.RemoveBook(bookId, username);
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Book removed successfully", HttpStatus.OK.value(), null));
        }
        catch(Exception e)
        {
            System.err.println("Could not remove book(ID: " + bookId + ") due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Failed to remove book: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping(value = "/Books")
    public ResponseEntity<PostmanResponseMessage<List<Book>>> GetAllPublishedBooks()
    {
        try
        {
            List<Book> books = bookService.GetAllPublishedBooks();
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Got a list of Published Books successfully", HttpStatus.OK.value(), books));
        }
        catch(Exception e)
        {
            System.err.println("Could not get the list of Published Books due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Could not get a list of Published Books due to exception: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }
}
