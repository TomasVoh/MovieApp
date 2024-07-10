package org.example.movieapp.Service;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MovieService extends PageReadAndWriteService<Movie>{
    PageDto<Movie> findMoviesByGenre(long id, int page, int size);
    PageDto<Movie> findMovieByCountry(long id, int page, int size);
    PageDto<Movie> findMoviesWithFilter(String nameLike, int minLength, int maxLength, LocalDate minDate, LocalDate maxDate, List<Genre> genres, List<Country> countries, List<Actor> actors, List<Director> directors, int page, int size);
    List<Movie> findAll();
}
