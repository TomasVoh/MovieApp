package org.example.movieapp.Service;

import org.example.movieapp.Model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewService extends PageReadAndWriteService<Review> {
}
