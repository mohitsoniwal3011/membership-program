package com.firstclub.membership.repository;

import com.firstclub.membership.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Tier} entities.
 * <p>
 * Provides standard CRUD operations.
 * </p>
 */
@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {
}
