package org.example.movieapp.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Table(name = "movies")
@Data
@AllArgsConstructor
@Audited
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String description;
    @Column(nullable = false, updatable = false)
    private LocalDate releaseDate;
    @Column(nullable = false)
    private int length;
    @Column(nullable = false)
    private String imagePath;
    @JsonBackReference
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
    )
    private List<Genre> genres;
    @JsonManagedReference
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "actors_movies",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id")
    )
    private List<Actor> actors;
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "movies_country",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
    )
    private List<Country> countries;
    @NotAudited
    @JsonIgnore
    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY)
    private List<UserEntity> users;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "directors_movies",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id")
    )
    private List<Director> directors;
    @NotAudited
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Review> reviews;

    @Override
    public boolean equals(Object o) {
        Movie movie = (Movie) o;
        return  movie.getName().equals(this.name) &&
                movie.getDescription().equals(this.getDescription()) &&
                movie.getReleaseDate().equals(this.getReleaseDate()) &&
                movie.getLength() == this.getLength() &&
                movie.getImagePath().equals(this.getImagePath()) &&
                movie.getGenres().equals(this.getGenres()) &&
                movie.getCountries().equals(this.getCountries()) &&
                movie.getActors().equals(this.getActors()) &&
                movie.getDirectors().equals(this.getDirectors());
    }
}
