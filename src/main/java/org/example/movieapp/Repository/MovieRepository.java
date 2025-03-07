package org.example.movieapp.Repository;

import org.example.movieapp.Model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :id")
    Page<Movie> findMovieByGenre(@Param("id") Long id, Pageable pageable);
    @Query("SELECT m FROM Movie m JOIN m.countries c WHERE c.id = :id")
    Page<Movie> findMovieByCountry(@Param("id") Long id, Pageable pageable);
}
