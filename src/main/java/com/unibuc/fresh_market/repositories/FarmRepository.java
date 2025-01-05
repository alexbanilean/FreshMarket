package com.unibuc.fresh_market.repositories;

import com.unibuc.fresh_market.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {
}
