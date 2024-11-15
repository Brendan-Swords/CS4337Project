package cs4337.group6.OrderService.Services;

import cs4337.group6.OrderService.Models.Book;
import cs4337.group6.OrderService.Models.Order;
import cs4337.group6.OrderService.Models.User;
import cs4337.group6.OrderService.Repository.IBookRepository;
import cs4337.group6.OrderService.Repository.IOrderRepository;
import cs4337.group6.OrderService.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IUserRepository userRepository;

    public Order CreateOrder(Order order)
    {
        User user = userRepository.findById(order.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        Book book = bookRepository.findById(order.getBookId()).orElseThrow(() -> new RuntimeException("Book Not Found"));
        return orderRepository.save(order);
    }

    public void DeleteOrder(Integer id) {
        Order orderToDelete = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));;
        orderRepository.delete(orderToDelete);
    }
}
