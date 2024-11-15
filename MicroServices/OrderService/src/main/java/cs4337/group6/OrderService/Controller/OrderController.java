package cs4337.group6.OrderService.Controller;

import cs4337.group6.OrderService.Models.Order;
import cs4337.group6.OrderService.Services.OrderService;
import cs4337.group6.OrderService.Utility.PostmanResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("CreateOrder")
    public ResponseEntity<PostmanResponseMessage<Order>> CreateOrder(@RequestBody Order order) {
        try
        {
            Order savedOrder = orderService.CreateOrder(order);
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Order created successfully", HttpStatus.OK.value(), savedOrder));
        }
        catch(Exception e)
        {
            System.err.println("Could not add Order due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Failed to create order: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @PostMapping(value = "/DeleteOrder/{id}", produces = "application/json")
    public ResponseEntity<PostmanResponseMessage<Order>> DeleteOrder(@PathVariable Integer id)
    {
        try
        {
            orderService.DeleteOrder(id);
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Order removed successfully", HttpStatus.OK.value(), null));
        }
        catch(Exception e)
        {
            System.err.println("Could not remove Order(ID: " + id + ") due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Failed to remove book: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
//        Optional<Order> order = orderService.getOrderById(id);
//        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
}
