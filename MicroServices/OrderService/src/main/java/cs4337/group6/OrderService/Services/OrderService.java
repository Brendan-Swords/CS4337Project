package cs4337.group6.OrderService.Services;

import cs4337.group6.OrderService.Models.Book;
import cs4337.group6.OrderService.Models.Order;
import cs4337.group6.OrderService.Models.User;
import cs4337.group6.OrderService.Repository.IBookRepository;
import cs4337.group6.OrderService.Repository.IOrderRepository;
import cs4337.group6.OrderService.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Order> GetAllOrders()
    {
        return orderRepository.findAll();
    }
}
