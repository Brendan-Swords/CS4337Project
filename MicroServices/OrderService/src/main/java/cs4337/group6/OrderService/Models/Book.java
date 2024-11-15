package cs4337.group6.OrderService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // To auto generate getters and setters (however the getters don't seem to work
      // when called in code).
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @Column(name = "ISBN")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The Getter for the books ID(ISBN). A second getter is added because Lombok
     * getters cannot be compiled.
     *
     * @return the book's ISBN.
     */
    public int GetId() {
        return this.id;
    }
}
