package cs4337.group6.Publish.Controllers;

import cs4337.group6.Publish.Models.PublishedBook;
import cs4337.group6.Publish.Services.PublishBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishedBookController
{
    @Autowired
    private PublishBookService bookService;

    @PostMapping("/PublishBook")
    private PublishedBook PublishBook(@RequestBody PublishedBook book)
    {
        return bookService.PublishBook(book);
    }
}
