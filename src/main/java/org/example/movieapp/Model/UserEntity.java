package org.example.movieapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100)
    private String surname;
    @Column(length = 100, nullable = false)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created;
    @Column(nullable = false)
    private String profileImagePath = "img/man-303792_640.png";
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )
    private List<Authority> authorities;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_favourity_movies",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id")
    )
    private List<Movie> movies;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_favourite_actors",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id")
    )
    private List<Actor> actors;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Review> reviews;

}
