package com.firstclub.membership.strategy;

import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import org.springframework.stereotype.Component;

/**
 * Strategy implementation that evaluates tier eligibility based on the user's cumulative historical order value.
 */
@Component
public class OrderValueStrategy implements TierEvaluationStrategy {

    /**
     * Evaluates if the user meets the minimum monetary order value required for the tier.
     *
     * @param user the user attempting to access or subscribe to the tier
     * @param tier the tier being evaluated
     * @return true if the tier has no minimum order value requirement, or if the user's total order value meets or exceeds it; false otherwise.
     */
    @Override
    public boolean isEligible(User user, Tier tier) {
        if (tier.getMinOrderValue() == null) return true; // No requirement
        return user.getTotalOrderValue() != null && user.getTotalOrderValue().compareTo(tier.getMinOrderValue()) >= 0;
    }
}
