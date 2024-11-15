package cs4337.group6.OrderService.Repository;

import cs4337.group6.OrderService.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book, Integer>
{

}

