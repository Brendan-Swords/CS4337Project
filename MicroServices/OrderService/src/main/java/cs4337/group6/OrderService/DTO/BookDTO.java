package cs4337.group6.OrderService.DTO;

import cs4337.group6.OrderService.Models.Book;
import cs4337.group6.OrderService.Models.User;

import java.util.List;
import java.util.stream.Collectors;

public class BookDTO {
    private String title;
    private List<String> buyers;

    public BookDTO(Book book) {
        this.title = book.getTitle();
        this.buyers = book.getBuyers().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
