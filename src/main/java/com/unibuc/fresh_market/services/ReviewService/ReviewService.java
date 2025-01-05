package com.unibuc.fresh_market.services.ReviewService;

import com.unibuc.fresh_market.domain.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review);
    Review getReviewById(Integer id);
    List<Review> getAllReviews();
    Review updateReview(Integer id, Review review);
    void deleteReview(Integer id);

    List<Review> getReviewsByFarmerId(Integer farmerId);
    double calculateAverageRatingByFarmerId(Integer farmerId);
}
