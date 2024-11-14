package cs4337.group6.Publish.Repositories;

import cs4337.group6.Publish.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Self-Explanatory.
 */
public interface IBookRepository extends JpaRepository<Book, Integer>
{

}

