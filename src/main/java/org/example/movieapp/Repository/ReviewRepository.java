package org.example.movieapp.Repository;

import org.example.movieapp.Model.Movie;
import org.example.movieapp.Model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByMovie(Movie movie, Pageable pageable);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :id")
    BigDecimal findAverageRating(@Param("id") Long movieId);
}
