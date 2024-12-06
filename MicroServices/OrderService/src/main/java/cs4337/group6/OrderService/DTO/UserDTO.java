package cs4337.group6.OrderService.DTO;

import cs4337.group6.OrderService.Models.User;
import cs4337.group6.OrderService.Models.Book;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String username;
    private List<String> purchasedBooks;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.purchasedBooks = user.getPurchasedBooks().stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }
}