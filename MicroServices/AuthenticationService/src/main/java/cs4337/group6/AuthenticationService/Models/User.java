package cs4337.group6.AuthenticationService.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
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

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    @JsonIgnore // Exclude from serialization
    private Set<Book> publishedBooks = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "UserBooks",
            joinColumns = @JoinColumn(name = "Username"),
            inverseJoinColumns = @JoinColumn(name = "ISBN")
    )

    private Set<Book> purchasedBooks = new HashSet<>();

    /**
     * The Getter for the users ID. A second getter is added because Lombok getters cannot be compiled.
     * @return the user's ID.
     */
    public int GetId()
    {
        return this.id;
    }

    public Set<Book> getPublishedBooks() {
        return Collections.unmodifiableSet(publishedBooks);
    }
}