package org.example.movieapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "actors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100)
    private String surname;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(nullable = false)
    private String imagePath;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private List<Movie> movies;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "actor_countries",
            joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
    )
    private List<Country> countries;
}
