package cs4337.group6.OrderService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data //To auto generate getters and setters (however the getters don't seem to work when called in code).
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The Getter for the users ID. A second getter is added because Lombok getters cannot be compiled.
     * @return the user's ID.
     */
    public int GetId()
    {
        return this.id;
    }
}