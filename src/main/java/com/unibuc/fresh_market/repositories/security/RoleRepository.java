package com.unibuc.fresh_market.repositories.security;

import com.unibuc.fresh_market.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
