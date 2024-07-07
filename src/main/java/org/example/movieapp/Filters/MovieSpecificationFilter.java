package org.example.movieapp.Filters;

import jakarta.persistence.criteria.Join;
import org.example.movieapp.Model.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class MovieSpecificationFilter {
    public static Specification<Movie> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Movie> lengthInterval(int min, int max) {
        return (root, query, builder) -> builder.between(root.get("length"), min, max);
    }

    public static Specification<Movie> releaseDateInterval(LocalDate min, LocalDate max) {
        return (root, query, builder) -> builder.between(root.get("releaseDate"), min, max);
    }

    public static Specification<Movie> genreEquals(List<Genre> genres) {
        return (root, query, builder) -> root.join("genres").in(genres);
    }

    public static Specification<Movie> countryEquals(List<Country> countries) {
        return (root, query, builder) -> root.join("countries").in(countries);
    }

    public static Specification<Movie> actorEquals(List<Actor> actors) {
        return (root, query, builder) -> root.join("actors").in(actors);
    }

    public static Specification<Movie> directorEquals(List<Director> directors) {
        return (root, query, builder) -> root.join("directors").in(directors);
    }
}
