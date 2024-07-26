package org.example.movieapp.Service;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;

public interface ReviewService extends PageReadAndWriteService<Review> {
    PageDto<Review> findByMovie(int page, int size, Movie movie);
    BigDecimal findAverageRatingByMovie(long movieId);
    void delete(long id);
}
