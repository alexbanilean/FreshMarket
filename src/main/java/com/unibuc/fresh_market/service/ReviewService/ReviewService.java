package com.unibuc.fresh_market.service.ReviewService;

import com.unibuc.fresh_market.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review createReview(Review review);
    Optional<Review> getReviewById(Integer id);
    List<Review> getAllReviews();
    Optional<Review> updateReview(Integer id, Review review);
    Optional<Review> deleteReview(Integer id);

    List<Review> getReviewsByFarmId(Integer farmId);
    double calculateAverageRatingByFarmId(Integer farmId);
}
