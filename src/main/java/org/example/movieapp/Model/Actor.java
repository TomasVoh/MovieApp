package org.example.movieapp.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

@Entity
@Table(name = "actors")
@Audited
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
    @Column(nullable = false, updatable = false)
    private LocalDate birthday;
    @Column(nullable = false)
    private String imagePath;
    @JsonBackReference
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private List<Movie> movies;
    @NotAudited
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "actor_countries",
            joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
    )
    private List<Country> countries;
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private List<UserEntity> users;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
