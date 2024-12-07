package cs4337.group6.OrderService.Controller;

import cs4337.group6.OrderService.Models.Order;
import cs4337.group6.OrderService.Services.OrderService;
import cs4337.group6.OrderService.Utility.PostmanResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/CreateOrder")
    public ResponseEntity<PostmanResponseMessage<Order>> CreateOrder(@RequestBody Order order) {
        try
        {
            String username = getUsernameFromAuthService();

            Order savedOrder = orderService.CreateOrder(order, username);
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

    @PostMapping(value = "/CancelOrder/{id}", produces = "application/json")
    public ResponseEntity<PostmanResponseMessage<Order>> DeleteOrder(@PathVariable Integer id)
    {
        try
        {
            String username = getUsernameFromAuthService();

            orderService.DeleteOrder(id, username);
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Order removed successfully", HttpStatus.OK.value(), null));
        }
        catch(Exception e)
        {
            System.err.println("Could not remove Order(ID: " + id + ") due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Failed to remove Order: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @PostMapping(value = "/CompleteOrder/{id}", produces = "application/json")
    public ResponseEntity<PostmanResponseMessage<Order>> CompleteOrder(@PathVariable Integer id)
    {
        try
        {
            String username = getUsernameFromAuthService();

            String orderResponse = orderService.CompleteOrder(id, username);
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>(orderResponse, HttpStatus.OK.value(), null));
        }
        catch(Exception e)
        {
            System.err.println("Could not fulfill Order(ID: " + id + ") due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Failed to fulfill Order: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping(value = "/Orders")
    public ResponseEntity<PostmanResponseMessage<List<Order>>> GetAllOrders()
    {
        try
        {
            List<Order> orders = orderService.GetAllOrders();
            return ResponseEntity.ok(
                    new PostmanResponseMessage<>("Got a list of Orders successfully", HttpStatus.OK.value(), orders));
        }
        catch(Exception e)
        {
            System.err.println("Could not get the list of Orders due to exception: "  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostmanResponseMessage<>("Could not get all Orders due to exception: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    private String getUsernameFromAuthService() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Request", "true");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://authentication-service:8080/auth/Username",
                HttpMethod.GET,
                entity,
                String.class
        );

        String username = response.getBody();
        return username;
    }
}
