package cs4337.group6.Services;

import cs4337.group6.Models.Order;
import cs4337.group6.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate; // For external service calls

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void DeleteOrder(Integer orderId) {
        Order orderToDelete = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Book Not Found"));;
        orderRepository.delete(orderToDelete);
    }

}
