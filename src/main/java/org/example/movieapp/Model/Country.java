package org.example.movieapp.Model;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, length = 10)
    private String code;
    @ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY)
    private List<Actor> actors;
    @ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY)
    private List<Movie> movies;
    @ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY)
    private List<Director> directors;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
