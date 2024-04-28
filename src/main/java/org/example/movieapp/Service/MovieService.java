package org.example.movieapp.Service;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService extends PageReadAndWriteService<Movie>{
    PageDto<Movie> findMoviesByGenre(long id, int page, int size);
}
