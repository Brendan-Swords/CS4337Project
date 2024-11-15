package cs4337.group6.PublishingService.Repositories;

import cs4337.group6.PublishingService.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Self-Explanatory.
 */
public interface IBookRepository extends JpaRepository<Book, Integer>
{

}

