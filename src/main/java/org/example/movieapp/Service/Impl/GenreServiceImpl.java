package org.example.movieapp.Service.Impl;

import org.example.movieapp.Model.Genre;
import org.example.movieapp.Repository.GenreRepository;
import org.example.movieapp.Service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;
    private Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = genreRepository.findAll();
        logger.trace("genres: {}", genres);
        return genres;
    }

    @Override
    public Genre findById(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(NoSuchElementException::new);
        logger.trace("genre: {}", genre);
        return genre;
    }
}
