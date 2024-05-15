package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Review;
import org.example.movieapp.Repository.ReviewRepository;
import org.example.movieapp.Service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        return null;
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
}
