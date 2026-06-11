package com.firstclub.membership.service;

import com.firstclub.membership.entity.Plan;
import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.repository.PlanRepository;
import com.firstclub.membership.repository.TierRepository;
import com.firstclub.membership.repository.UserRepository;
import com.firstclub.membership.strategy.TierEvaluationStrategy;
import com.firstclub.membership.constant.ErrorMessages;
import com.firstclub.membership.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for managing membership catalog queries such as retrieving
 * available plans, tiers, and determining tier eligibility for specific users.
 */
@Service
@RequiredArgsConstructor
public class MembershipCatalogService {
    
    private final PlanRepository planRepository;
    private final TierRepository tierRepository;
    private final UserRepository userRepository;
    
    /**
     * The list of injected strategies that govern tier unlocking rules.
     */
    private final List<TierEvaluationStrategy> strategies;

    /**
     * Retrieves all available membership plans from the database.
     *
     * @return a list of {@link Plan}s
     */
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    /**
     * Retrieves all defined membership tiers from the database.
     *
     * @return a list of {@link Tier}s
     */
    public List<Tier> getAllTiers() {
        return tierRepository.findAll();
    }

    /**
     * Retrieves only the tiers that a given user is eligible to unlock.
     *
     * @param userId the ID of the user to evaluate
     * @return a list of {@link Tier}s the user satisfies the criteria for
     * @throws RuntimeException if the user cannot be found
     */
    public List<Tier> getEligibleTiersForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        
        List<Tier> allTiers = tierRepository.findAll();
        
        return allTiers.stream()
                .filter(tier -> isUserEligibleForTier(user, tier))
                .collect(Collectors.toList());
    }

    /**
     * Evaluates whether a user is eligible for a specific tier by applying
     * all registered {@link TierEvaluationStrategy} instances. 
     * The user must satisfy ALL constraints.
     *
     * @param user the user attempting to access the tier
     * @param tier the tier being evaluated
     * @return true if the user passes all strategy checks; false otherwise
     */
    public boolean isUserEligibleForTier(User user, Tier tier) {
        return strategies.stream()
                .allMatch(strategy -> strategy.isEligible(user, tier));
    }
}
