package org.example.movieapp.Filters;

import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Movie;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class ActorSpecificationFilter {
    public static Specification<Actor> nameLike(String actorName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + actorName + "%");
    }

    public static Specification<Actor> surnameLike(String surname) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
    }

    public static Specification<Actor> birthdayInterval(LocalDate start, LocalDate end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("birthday"), start, end);
    }

    public static Specification<Actor> countryEquals(List<Country> countries) {
        return (root, query, criteriaBuilder) -> root.join("countries").in(countries);
    }

    public static Specification<Actor> moviesEquals(List<Movie> movies) {
        return (root, query, criteriaBuilder) -> root.join("movies").in(movies);
    }
}
