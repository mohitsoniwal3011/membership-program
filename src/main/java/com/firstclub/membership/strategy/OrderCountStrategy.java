package com.firstclub.membership.strategy;

import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import org.springframework.stereotype.Component;

/**
 * Strategy implementation that evaluates tier eligibility based on the user's historical order count.
 */
@Component
public class OrderCountStrategy implements TierEvaluationStrategy {

    /**
     * Evaluates if the user meets the minimum order count required for the tier.
     *
     * @param user the user attempting to access or subscribe to the tier
     * @param tier the tier being evaluated
     * @return true if the tier has no minimum order requirement, or if the user's total orders meet or exceed it; false otherwise.
     */
    @Override
    public boolean isEligible(User user, Tier tier) {
        if (tier.getMinOrders() == null) return true; // No requirement
        return user.getTotalOrders() != null && user.getTotalOrders() >= tier.getMinOrders();
    }
}
