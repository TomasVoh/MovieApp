package org.example.movieapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "directors")
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100)
    private String surname;
    @Column(nullable = false, updatable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private String imagePath;
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "director_countries",
            joinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
    )
    private List<Country> countries;
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "directors")
    private List<Movie> movies;
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "directors")
    private List<UserEntity> users;

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        Director director = (Director) o;
        if(director.getName().equals(this.getName()) &&
        director.getSurname().equals(this.getSurname()) &&
        director.getDateOfBirth().equals(this.getDateOfBirth()) &&
        director.getCountries().equals(this.getCountries()) &&
        director.getMovies().equals(this.getMovies())) {
            return true;
        }
        return false;
    }
}
