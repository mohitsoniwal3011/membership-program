package com.firstclub.membership.strategy;

import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import org.springframework.stereotype.Component;

/**
 * Strategy to evaluate if a user belongs to the required cohort for a specific tier.
 */
@Component
public class CohortStrategy implements TierEvaluationStrategy {

    /**
     * Determines eligibility based on the user's cohort matching the tier's required cohort.
     *
     * @param user the user being evaluated
     * @param tier the tier being checked
     * @return true if the tier has no cohort restriction, or if the user's cohort matches the tier's required cohort.
     */
    @Override
    public boolean isEligible(User user, Tier tier) {
        if (tier.getAllowedCohort() == null) {
            return true;
        }
        return tier.getAllowedCohort().equalsIgnoreCase(user.getCohortType());
    }
}
