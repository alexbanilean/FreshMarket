package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.repository.ReviewRepository;
import com.unibuc.fresh_market.service.ReviewService.ReviewServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplementationTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImplementation reviewService;

    private Farm farm;
    private Review review;

    @BeforeEach
    void setUp() {
        farm = Farm.builder().id(1).name("Farm 1").address("Address 1").build();
        review = Review.builder()
                .id(1)
                .content("Great product!")
                .rating(5)
                .farm(farm)
                .build();
    }

    @Test
    void whenCreateReview_thenReturnCreatedReview() {
        // Given
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // When
        Review createdReview = reviewService.createReview(review);

        // Then
        assertNotNull(createdReview);
        assertEquals(review.getId(), createdReview.getId());
        assertEquals(review.getContent(), createdReview.getContent());
        assertEquals(review.getRating(), createdReview.getRating());
        assertEquals(review.getFarm(), createdReview.getFarm());

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void whenGetReviewById_thenReturnReview() {
        // Given
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // When
        Optional<Review> result = reviewService.getReviewById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(review.getId(), result.get().getId());
        assertEquals(review.getContent(), result.get().getContent());

        verify(reviewRepository, times(1)).findById(1);
    }

    @Test
    void whenGetAllReviews_thenReturnListOfReviews() {
        // Given
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        // When
        List<Review> reviews = reviewService.getAllReviews();

        // Then
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertEquals(1, reviews.size());
        assertEquals(review.getId(), reviews.getFirst().getId());

        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void whenUpdateReview_thenReturnUpdatedReview() {
        // Given
        Review updatedReview = Review.builder()
                .id(1)
                .content("Updated review content")
                .rating(4)
                .farm(farm)
                .build();
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);

        // When
        Optional<Review> result = reviewService.updateReview(1, updatedReview);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedReview.getContent(), result.get().getContent());
        assertEquals(updatedReview.getRating(), result.get().getRating());

        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void whenDeleteReview_thenReturnDeletedReview() {
        // Given
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // When
        Optional<Review> result = reviewService.deleteReview(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(review.getId(), result.get().getId());

        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void whenGetReviewsByFarmId_thenReturnListOfReviews() {
        // Given
        when(reviewRepository.getReviewsByFarmId(1)).thenReturn(List.of(review));

        // When
        List<Review> result = reviewService.getReviewsByFarmId(1);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(review.getId(), result.getFirst().getId());

        verify(reviewRepository, times(1)).getReviewsByFarmId(1);
    }

    @Test
    void whenCalculateAverageRatingByFarmId_thenReturnCorrectAverage() {
        // Given
        List<Review> reviews = List.of(
                Review.builder().rating(4).build(),
                Review.builder().rating(5).build(),
                Review.builder().rating(3).build()
        );
        when(reviewRepository.getReviewsByFarmId(1)).thenReturn(reviews);

        // When
        double averageRating = reviewService.calculateAverageRatingByFarmId(1);

        // Then
        assertEquals(4.0, averageRating);

        verify(reviewRepository, times(1)).getReviewsByFarmId(1);
    }

    @Test
    void whenCalculateAverageRatingByFarmIdWithNoReviews_thenReturnZero() {
        // Given
        when(reviewRepository.getReviewsByFarmId(1)).thenReturn(List.of());

        // When
        double averageRating = reviewService.calculateAverageRatingByFarmId(1);

        // Then
        assertEquals(0.0, averageRating);

        verify(reviewRepository, times(1)).getReviewsByFarmId(1);
    }
}

