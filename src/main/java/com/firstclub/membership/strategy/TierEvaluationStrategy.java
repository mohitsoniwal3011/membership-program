package com.firstclub.membership.strategy;

import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;

/**
 * Interface defining the contract for membership Tier Evaluation Strategies.
 * <p>
 * This follows the Strategy Design Pattern. Multiple implementations of this interface
 * can be defined (e.g., OrderCount, Cohort, Revenue) to dynamically evaluate
 * if a user qualifies for a specific membership tier, ensuring Open/Closed principle.
 * </p>
 */
public interface TierEvaluationStrategy {

    /**
     * Evaluates whether a given user is eligible for a given tier.
     *
     * @param user the user to evaluate
     * @param tier the tier to check eligibility against
     * @return true if the user meets the requirements of this specific strategy; false otherwise.
     */
    boolean isEligible(User user, Tier tier);
}
