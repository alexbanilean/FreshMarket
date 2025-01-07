package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.service.ReviewService.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ReviewController reviewController;

    private Review review;
    private List<Review> reviewList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        review = Review.builder()
                .id(1)
                .rating(4)
                .content("Great product!")
                .build();

        Review review2 = Review.builder()
                .id(2)
                .rating(5)
                .content("Excellent quality!")
                .build();

        reviewList = List.of(review, review2);
    }

    @Test
    void givenValidReview_whenCreateReview_thenReturnCreatedReview() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(reviewService.createReview(any(Review.class))).thenReturn(review);

        // When
        ResponseEntity<?> response = reviewController.createReview(review, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(review, response.getBody());
    }

    @Test
    void givenInvalidReview_whenCreateReview_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = reviewController.createReview(review, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenReviews_whenGetAllReviews_thenReturnReviewList() {
        // Given
        when(reviewService.getAllReviews()).thenReturn(reviewList);

        // When
        ResponseEntity<?> response = reviewController.getAllReviews();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(reviewList, response.getBody());
    }

    @Test
    void givenValidReviewId_whenGetReviewById_thenReturnReview() {
        // Given
        Integer reviewId = 1;
        when(reviewService.getReviewById(reviewId)).thenReturn(Optional.of(review));

        // When
        ResponseEntity<?> response = reviewController.getReviewById(String.valueOf(reviewId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(review, response.getBody());
    }

    @Test
    void givenInvalidReviewId_whenGetReviewById_thenReturnNotFound() {
        // Given
        Integer reviewId = 1;
        when(reviewService.getReviewById(reviewId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = reviewController.getReviewById(String.valueOf(reviewId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidReview_whenUpdateReview_thenReturnUpdatedReview() {
        // Given
        Integer reviewId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(reviewService.updateReview(eq(reviewId), any(Review.class))).thenReturn(Optional.of(review));

        // When
        ResponseEntity<?> response = reviewController.updateReview(review, bindingResult, String.valueOf(reviewId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(review, response.getBody());
    }

    @Test
    void givenInvalidReview_whenUpdateReview_thenReturnBadRequest() {
        // Given
        Integer reviewId = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = reviewController.updateReview(review, bindingResult, String.valueOf(reviewId));

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenInvalidReviewId_whenUpdateReview_thenReturnNotFound() {
        // Given
        Integer reviewId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(reviewService.updateReview(reviewId, review)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = reviewController.updateReview(review, bindingResult, String.valueOf(reviewId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidReviewId_whenDeleteReview_thenReturnDeletedReview() {
        // Given
        Integer reviewId = 1;
        when(reviewService.deleteReview(reviewId)).thenReturn(Optional.of(review));

        // When
        ResponseEntity<?> response = reviewController.deleteReviewById(String.valueOf(reviewId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(review, response.getBody());
    }

    @Test
    void givenInvalidReviewId_whenDeleteReview_thenReturnNotFound() {
        // Given
        Integer reviewId = 1;
        when(reviewService.deleteReview(reviewId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = reviewController.deleteReviewById(String.valueOf(reviewId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidFarmId_whenGetReviewsByFarmId_thenReturnReviewList() {
        // Given
        Integer farmId = 1;
        when(reviewService.getReviewsByFarmId(farmId)).thenReturn(reviewList);

        // When
        ResponseEntity<?> response = reviewController.getReviewsByFarmId(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(reviewList, response.getBody());
    }

    @Test
    void givenValidFarmId_whenGetAverageRatingByFarmId_thenReturnAverageRating() {
        // Given
        Integer farmId = 1;
        double averageRating = 4.75;
        when(reviewService.calculateAverageRatingByFarmId(farmId)).thenReturn(averageRating);

        // When
        ResponseEntity<?> response = reviewController.getAverageReviewsByFarmId(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(averageRating, response.getBody());
    }
}
