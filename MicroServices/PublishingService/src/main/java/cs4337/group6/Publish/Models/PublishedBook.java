package cs4337.group6.Publish.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data //To auto generate getters and setters (however the getters don't seem to work when called in code).
@Table(name = "PublishedBooks")
@NoArgsConstructor
@AllArgsConstructor
public class PublishedBook
{
    @Id
    @Column(name = "ISBN")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer isbn;

    @Column(name = "Publisher", nullable = false)
    private String publisher;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    /**
     * The Getter for the books ID(ISBN). A second getter is added because Lombok getters cannot be compiled.
     * @return the book's ISBN.
     */
    public int GetIsbn()
    {
        return this.isbn;
    }
}
