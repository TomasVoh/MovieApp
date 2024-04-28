package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Mapper.PageMapper;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.MovieRepository;
import org.example.movieapp.Service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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
}
