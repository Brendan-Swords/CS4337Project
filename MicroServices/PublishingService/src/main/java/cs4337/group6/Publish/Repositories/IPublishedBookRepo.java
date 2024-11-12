package cs4337.group6.Publish.Repositories;

import cs4337.group6.Publish.Models.PublishedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPublishedBookRepo extends JpaRepository<PublishedBook, Integer>
{
}
