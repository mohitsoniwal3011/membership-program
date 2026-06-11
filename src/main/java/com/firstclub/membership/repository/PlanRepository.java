package com.firstclub.membership.repository;

import com.firstclub.membership.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Plan} entities.
 * <p>
 * Provides standard CRUD operations.
 * </p>
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
