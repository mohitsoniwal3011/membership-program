package com.firstclub.membership.service;

import com.firstclub.membership.entity.*;
import com.firstclub.membership.repository.PlanRepository;
import com.firstclub.membership.repository.SubscriptionRepository;
import com.firstclub.membership.repository.TierRepository;
import com.firstclub.membership.repository.UserRepository;
import com.firstclub.membership.constant.ErrorMessages;
import com.firstclub.membership.exception.BusinessValidationException;
import com.firstclub.membership.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Core business service handling the lifecycle of user subscriptions.
 * Enforces business rules, eligibility checks, and transaction boundaries.
 */
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TierRepository tierRepository;
    private final MembershipCatalogService catalogService;

    /**
     * Subscribes a user to a selected plan and tier.
     * Cancels any pre-existing active subscriptions the user holds.
     *
     * @param userId the ID of the user subscribing
     * @param planId the ID of the chosen plan
     * @param tierId the ID of the chosen tier
     * @return the newly persisted {@link Subscription} in ACTIVE status
     * @throws RuntimeException if entities are not found or user is ineligible for the tier
     */
    @Transactional
    public Subscription subscribe(Long userId, Long planId, Long tierId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PLAN_NOT_FOUND));
        Tier tier = tierRepository.findById(tierId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.TIER_NOT_FOUND));

        // Defense in depth check
        if (!catalogService.isUserEligibleForTier(user, tier)) {
            throw new BusinessValidationException(ErrorMessages.USER_NOT_ELIGIBLE_FOR_TIER + tier.getName());
        }

        // Cancel any existing active subscriptions first (business logic choice)
        List<Subscription> existing = subscriptionRepository.findByUserId(userId).stream()
                .filter(s -> s.getStatus() == SubscriptionStatus.ACTIVE)
                .toList();
        for (Subscription sub : existing) {
            sub.cancel(); // Encapsulated OOP logic
            subscriptionRepository.save(sub);
        }

        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .tier(tier)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(plan.getDurationDays()))
                .status(SubscriptionStatus.ACTIVE)
                .build();

        return subscriptionRepository.save(subscription);
    }

    /**
     * Modifies an existing subscription. Can be used for Upgrades, Downgrades, or Plan extensions.
     *
     * @param subId     the ID of the subscription to modify
     * @param newPlanId the optional new plan ID
     * @param newTierId the optional new tier ID
     * @return the updated {@link Subscription}
     * @throws RuntimeException if subscription is inactive or user is ineligible for the new tier
     */
    @Transactional
    public Subscription modifySubscription(Long subId, Long newPlanId, Long newTierId) {
        if (newPlanId == null && newTierId == null) {
            throw new BusinessValidationException("Both newPlanId and newTierId cannot be null.");
        }

        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SUBSCRIPTION_NOT_FOUND));

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new BusinessValidationException(ErrorMessages.ONLY_ACTIVE_SUBS_MODIFIABLE);
        }

        if (newPlanId != null) {
            Plan plan = planRepository.findById(newPlanId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PLAN_NOT_FOUND));
            subscription.updatePlan(plan); // Encapsulated OOP logic
        }

        if (newTierId != null) {
            Tier tier = tierRepository.findById(newTierId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.TIER_NOT_FOUND));
            // Defense in depth check
            if (!catalogService.isUserEligibleForTier(subscription.getUser(), tier)) {
                throw new BusinessValidationException(ErrorMessages.USER_NOT_ELIGIBLE_FOR_TIER + tier.getName());
            }
            subscription.updateTier(tier); // Encapsulated OOP logic
        }

        return subscriptionRepository.save(subscription);
    }

    /**
     * Cancels an active subscription.
     *
     * @param subId the ID of the subscription to cancel
     * @return the updated {@link Subscription} in CANCELLED status
     * @throws RuntimeException if the subscription is already cancelled or not found
     */
    @Transactional
    public Subscription cancelSubscription(Long subId) {
        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SUBSCRIPTION_NOT_FOUND));
        
        subscription.cancel(); // Encapsulated OOP logic
        return subscriptionRepository.save(subscription);
    }

    /**
     * Retrieves all active subscriptions associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of ACTIVE {@link Subscription} entities for the user
     */
    public List<Subscription> getActiveSubscriptionsForUser(Long userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .filter(s -> s.getStatus() == SubscriptionStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}
