package org.example.movieapp.Filters;

import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Movie;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class DirectorSpecificationFilter {
    public static Specification<Director> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Director> surnameLike(String surname) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
    }

    public static Specification<Director> birthdayInterval(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("dateOfBirth"), startDate, endDate);
    }

    public static Specification<Director> countryEquals(List<Country> countries) {
        return (root, query, criteriaBuilder) -> root.join("countries").in(countries);
    }

    public static Specification<Director> movieEquals(List<Movie> movieList) {
        return (root, query, criteriaBuilder) -> root.join("movies").in(movieList);
    }
}
