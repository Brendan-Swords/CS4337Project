package cs4337.group6.Repositories;

import cs4337.group6.OrderService.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Self-Explanatory.
 */
public interface IBookRepository extends JpaRepository<Book, Integer>
{

}

