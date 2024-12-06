package cs4337.group6.PublishingService.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Book> publishedBooks = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "UserBooks",
            joinColumns = @JoinColumn(name = "Username"),
            inverseJoinColumns = @JoinColumn(name = "ISBN")
    )
    @JsonIgnore // Prevent serialization of purchasedBooks to avoid recursion
    private Set<Book> purchasedBooks = new HashSet<>();

    public void addPurchasedBook(Book book) {
        this.purchasedBooks.add(book);
        book.getBuyers().add(this); // Ensure bidirectional relationship
    }

    public void removePurchasedBook(Book book) {
        this.purchasedBooks.remove(book);
        book.getBuyers().remove(this); // Ensure bidirectional relationship
    }

    public Set<Book> getPublishedBooks() {
        return Collections.unmodifiableSet(publishedBooks);
    }

    public Set<Book> getPurchasedBooks() {
        return Collections.unmodifiableSet(purchasedBooks);
    }
}