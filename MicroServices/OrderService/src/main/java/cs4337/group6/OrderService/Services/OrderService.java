package cs4337.group6.OrderService.Services;

import cs4337.group6.OrderService.Models.Order;
import cs4337.group6.OrderService.Repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    public Order CreateOrder(Order order) {
        return orderRepository.save(order);
    }

    public void DeleteOrder(Integer orderId) {
        Order orderToDelete = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Book Not Found"));;
        orderRepository.delete(orderToDelete);
    }

}
