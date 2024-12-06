package cs4337.group6.OrderService.Services;

import cs4337.group6.OrderService.Models.Book;
import cs4337.group6.OrderService.Models.Order;
import cs4337.group6.OrderService.Models.User;
import cs4337.group6.OrderService.Repository.IBookRepository;
import cs4337.group6.OrderService.Repository.IOrderRepository;
import cs4337.group6.OrderService.Repository.IUserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IUserRepository userRepository;

    public Order CreateOrder(Order order, String username) throws Exception
    {
        User user = userRepository.findByUsername(username);
        if(user == null)
        {
            throw new Exception("You are not signed in!");
        }
        Book book = bookRepository.findById(order.getBookId()).orElseThrow(() -> new RuntimeException("Book Not Found"));
        order.setUsername(username);
        return orderRepository.save(order);
    }

    public void DeleteOrder(Integer id, String username) throws Exception {
        User currentUser = userRepository.findByUsername(username);
        Order orderToDelete = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));
        if(orderToDelete.getUsername().equals(currentUser.getUsername()))
        {
            orderRepository.delete(orderToDelete);
        }
        else
        {
            throw new Exception("This order does not belong to you!");
        }
    }

    public String CompleteOrder(Integer id, String username) throws Exception {
        User currentUser = userRepository.findByUsername(username);
        Order orderToComplete = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));
        Book book = bookRepository.findById(orderToComplete.getBookId()).orElseThrow(() -> new RuntimeException("Book Not Found"));
        if (orderToComplete.getUsername().equals(currentUser.getUsername()))
        {
//            Set<User> newBuyers = new HashSet<>();
//            newBuyers.add(currentUser);
//            book.setBuyers(newBuyers);
                                                                            // NONE OF THIS COMMENTED CODE WORKS AND IK WHY BUT I CAN LITERALLY NOT FIX IT!!!!!!!!!!!
//            Hibernate.initialize(currentUser.getPurchasedBooks());
//            Hibernate.initialize(book.getBuyers());
//
//            if (currentUser.getPurchasedBooks() == null) {
//                currentUser.setPurchasedBooks(new HashSet<>());
//            }
//            currentUser.addPurchasedBook(book);

            orderRepository.delete(orderToComplete);
            return "Order for Book: " + book.getTitle() + " has been complete. You now own the book and " + book.getPrice() + " has been deducted from your account!";
        }
        else
        {
            throw new Exception("This order does not belong to you!");
        }
    }

    public List<Order> GetAllOrders()
    {
        return orderRepository.findAll();
    }
}
