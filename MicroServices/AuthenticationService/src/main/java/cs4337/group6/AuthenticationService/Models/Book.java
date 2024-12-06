package cs4337.group6.AuthenticationService.Models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @Column(name = "ISBN")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PublisherID")
    @JsonIgnoreProperties("publishedBooks")
    private User publisher;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToMany(mappedBy = "purchasedBooks", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore // Prevent serialization of buyers to avoid recursion
    private Set<User> buyers = new HashSet<>();

    public void addBuyer(User user) {
        this.buyers.add(user);
        user.getPurchasedBooks().add(this); // Ensure bidirectional relationship
    }

    public void removeBuyer(User user) {
        this.buyers.remove(user);
        user.getPurchasedBooks().remove(this); // Ensure bidirectional relationship
    }

    public Set<User> getBuyers() {
        return Collections.unmodifiableSet(buyers);
    }
}