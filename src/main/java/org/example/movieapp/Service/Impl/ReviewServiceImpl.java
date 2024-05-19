package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Mapper.PageMapper;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Model.Review;
import org.example.movieapp.Repository.ReviewRepository;
import org.example.movieapp.Service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public PageDto<Review> findAllByPage(int page, int size) {
        Page<Review> reviewPage = reviewRepository.findAll(PageRequest.of(page, size));
        PageDto<Review> pageDto = PageMapper.pageMapper(reviewPage);
        return pageDto;
    }

    @Override
    public Review findById(long id) {
        Review review = reviewRepository.findById(id).orElseThrow(NoSuchElementException::new);
        logger.trace("review: {} with id: {}", review, id);
        return review;
    }

    @Override
    public Review save(Review entity) {
        Review savedReview = reviewRepository.save(entity);
        logger.trace("review: {}", savedReview);
        return savedReview;
    }

    @Override
    public PageDto<Review> findByMovie(int page, int size, Movie movie) {
        Page<Review> reviewPage = reviewRepository.findByMovie(movie, PageRequest.of(page, size));
        PageDto<Review> pageDto = PageMapper.pageMapper(reviewPage);
        return pageDto;
    }

    @Override
    public BigDecimal findAverageRatingByMovie(long movieId) {
        BigDecimal averageRating = reviewRepository.findAverageRating(movieId);
        logger.trace("averageRating: {}", averageRating);
        return averageRating;
    }
}
