package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.service.ReviewService.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Reviews controller")
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Create a new review")
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody @Valid Review review, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Review createdReview = reviewService.createReview(review);
        return ResponseEntity.ok(createdReview);
    }

    @Operation(summary = "Get all reviews")
    @GetMapping
    public ResponseEntity<?> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Get a review by id")
    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable String reviewId) {
        Integer id = Integer.parseInt(reviewId);
        Optional<Review> review = reviewService.getReviewById(id);
        if (review.isPresent()) {
            return ResponseEntity.ok(review.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a review")
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@RequestBody @Valid Review review, BindingResult bindingResult, @PathVariable String reviewId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Integer id = Integer.parseInt(reviewId);
        Optional<Review> updatedReview = reviewService.updateReview(id, review);
        if (updatedReview.isPresent()) {
            return ResponseEntity.ok(updatedReview.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a review")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReviewById(@PathVariable String reviewId) {
        Integer id = Integer.parseInt(reviewId);
        Optional<Review> deletedReview = reviewService.deleteReview(id);
        if (deletedReview.isPresent()) {
            reviewService.deleteReview(id);
            return ResponseEntity.ok(deletedReview.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get reviews for a farm")
    @GetMapping("/farm/{farmId}")
    public ResponseEntity<?> getReviewsByFarmId(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        List<Review> reviews = reviewService.getReviewsByFarmId(id);
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Get average rating for a farm")
    @GetMapping("/farm/{farmId}/average")
    public ResponseEntity<?> getAverageReviewsByFarmId(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        double average = reviewService.calculateAverageRatingByFarmId(id);
        return ResponseEntity.ok(average);
    }
}
