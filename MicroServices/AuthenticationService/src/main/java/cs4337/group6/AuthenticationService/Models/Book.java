package cs4337.group6.AuthenticationService.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data //To auto generate getters and setters (however the getters don't seem to work when called in code).
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book
{
    @Id
    @Column(name = "ISBN")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PublisherID")
    @JsonIgnoreProperties("publishedBooks") // Prevent accessing publishedBooks in User
    @JsonProperty
    private User publisher;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToMany(mappedBy = "purchasedBooks")
    private Set<User> buyers = new HashSet<>();

    /**
     * The Getter for the books ID(ISBN). A second getter is added because Lombok getters cannot be compiled.
     * @return the book's ISBN.
     */
    public int GetId()
    {
        return this.id;
    }
}

