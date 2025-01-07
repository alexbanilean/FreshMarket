package com.unibuc.fresh_market.repository;

import com.unibuc.fresh_market.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query(value = "SELECT r FROM Review r WHERE r.farm.id = :farmId")
    List<Review> getReviewsByFarmId(Integer farmId);
}
