package cs4337.group6.OrderService.Controller;

import cs4337.group6.OrderService.Models.Order;
import cs4337.group6.OrderService.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/CreateOrder")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.CreateOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PostMapping("/DeleteOrder")
    public void deleteOrder(@RequestBody Integer id)
    {
        orderService.DeleteOrder(id);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
//        Optional<Order> order = orderService.getOrderById(id);
//        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
}
