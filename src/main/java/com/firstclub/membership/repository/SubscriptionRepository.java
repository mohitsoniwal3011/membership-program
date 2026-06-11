package com.firstclub.membership.repository;

import com.firstclub.membership.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Subscription} entities.
 * <p>
 * Provides standard CRUD operations along with custom queries to fetch user subscriptions.
 * </p>
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    /**
     * Retrieves all subscriptions (active, cancelled, expired, pending) associated with a user.
     *
     * @param userId the ID of the user whose subscriptions are being fetched
     * @return a list of all {@link Subscription} records for the user
     */
    List<Subscription> findByUserId(Long userId);
}
