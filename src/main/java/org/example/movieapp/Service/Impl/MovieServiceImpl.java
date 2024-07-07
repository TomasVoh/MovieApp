package org.example.movieapp.Service.Impl;

import io.micrometer.common.util.StringUtils;
import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Filters.MovieSpecificationFilter;
import org.example.movieapp.Mapper.PageMapper;
import org.example.movieapp.Model.*;
import org.example.movieapp.Repository.GenreRepository;
import org.example.movieapp.Repository.MovieRepository;
import org.example.movieapp.Service.MovieImportExportService;
import org.example.movieapp.Service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    private Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public PageDto<Movie> findAllByPage(int page, int size) {
        Page<Movie> movies = movieRepository.findAll(PageRequest.of(page, size));
        PageDto<Movie> pageDto = PageMapper.pageMapper(movies);
        logger.trace("Paged movies: {}", pageDto);
        return pageDto;
    }

    @Override
    public Movie findById(long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(NoSuchElementException::new);
        logger.trace("movie with id: {}", movie);
        return movie;
    }

    @Override
    public Movie save(Movie entity) {
        Movie savedMovie = movieRepository.save(entity);
        logger.trace("saved movie: {}", savedMovie);
        return savedMovie;
    }

    @Override
    public PageDto<Movie> findMoviesByGenre(long id, int page, int size) {
        Page<Movie> movies = movieRepository.findMovieByGenre(id, PageRequest.of(page, size));
        PageDto<Movie> pageMovies = PageMapper.pageMapper(movies);
        logger.trace("Paged movies by section : {}", pageMovies);
        return pageMovies;
    }

    @Override
    public PageDto<Movie> findMovieByCountry(long id, int page, int size) {
        Page<Movie> movies = movieRepository.findMovieByCountry(id, PageRequest.of(page, size));
        PageDto<Movie> pageMovies = PageMapper.pageMapper(movies);
        logger.trace("Paged movies by country : {}", pageMovies);
        return pageMovies;
    }

    @Override
    public PageDto<Movie> findMoviesWithFilter(String nameLike, int minLength, int maxLength, LocalDate minDate, LocalDate maxDate, List<Genre> genres, List<Country> countries, List<Actor> actors, List<Director> directors, int page, int size) {
        Specification<Movie> specification = Specification.where(StringUtils.isBlank(nameLike) ? null : MovieSpecificationFilter.nameLike(nameLike))
                .and(MovieSpecificationFilter.lengthInterval(minLength, maxLength)
                        .and(minDate != null  && maxDate != null ? MovieSpecificationFilter.releaseDateInterval(minDate, maxDate) : null)
                        .and(genres != null ? MovieSpecificationFilter.genreEquals(genres) : null)
                        .and(actors != null ? MovieSpecificationFilter.actorEquals(actors) : null)
                        .and(countries != null ? MovieSpecificationFilter.countryEquals(countries) : null)
                        .and(directors != null ? MovieSpecificationFilter.directorEquals(directors) : null));
        Page<Movie> movies = movieRepository.findAll(specification, PageRequest.of(page, size));
        PageDto<Movie> pageDto = PageMapper.pageMapper(movies);
        return pageDto;
    }
}
