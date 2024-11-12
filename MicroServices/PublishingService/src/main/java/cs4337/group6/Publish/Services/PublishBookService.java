package cs4337.group6.Publish.Services;

import cs4337.group6.Publish.Models.PublishedBook;
import cs4337.group6.Publish.Repositories.IPublishedBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishBookService
{
    @Autowired
    private IPublishedBookRepo bookRepo;

    public PublishedBook PublishBook(PublishedBook bookToPublish)
    {
        return bookRepo.save(bookToPublish);
    }
}
