package com.unibuc.fresh_market.services.ReviewService;

import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImplementation implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImplementation(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(Integer id) {
        Review review = reviewRepository.findById(id).orElse(null);
        return Optional.ofNullable(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> updateReview(Integer id, Review review) {
       Review reviewToUpdate = reviewRepository.findById(id).orElse(null);

       if (reviewToUpdate != null) {
           reviewToUpdate.setContent(review.getContent());
           reviewToUpdate.setRating(review.getRating());

           Review updatedReview = reviewRepository.save(reviewToUpdate);
           return Optional.of(updatedReview);
       }

       return Optional.empty();
    }

    public Optional<Review> deleteReview(Integer id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            reviewRepository.delete(review);
            return Optional.of(review);
        }

        return Optional.empty();
    }

    public List<Review> getReviewsByFarmId(Integer farmId) {
        return reviewRepository.getReviewsByFarmId(farmId);
    }

    public double calculateAverageRatingByFarmId(Integer farmId) {
        List<Review> reviews = getReviewsByFarmId(farmId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double totalRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();

        return totalRating / reviews.size();
    }
}
