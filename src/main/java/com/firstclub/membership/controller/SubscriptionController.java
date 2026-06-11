package com.firstclub.membership.controller;

import com.firstclub.membership.dto.ModifySubscriptionRequest;
import com.firstclub.membership.dto.SubscriptionRequest;
import com.firstclub.membership.entity.Subscription;
import com.firstclub.membership.service.SubscriptionService;
import com.firstclub.membership.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * REST Controller responsible for managing the lifecycle of User Subscriptions,
 * including subscribing, modifying (upgrading/downgrading), and cancelling.
 */
@RestController
@RequestMapping(AppConstants.API_V1_SUBSCRIPTIONS)
@RequiredArgsConstructor
public class SubscriptionController {
    
    private final SubscriptionService subscriptionService;

    /**
     * Creates a new active subscription for a user.
     * <p>
     * Any previously active subscriptions for this user will be cancelled.
     * </p>
     *
     * @param request the payload containing user ID, selected plan ID, and selected tier ID
     * @return the newly created {@link Subscription} entity.
     */
    @PostMapping
    public Subscription createSubscription(@Valid @RequestBody SubscriptionRequest request) {
        return subscriptionService.subscribe(request.userId(), request.planId(), request.tierId());
    }

    /**
     * Modifies an existing active subscription (e.g., upgrading to a higher tier or changing plan duration).
     * <p>
     * Defense-in-depth validates that the user is eligible for the newly requested tier.
     * </p>
     *
     * @param subId the ID of the subscription to modify
     * @param request the payload containing the optional new plan ID and/or new tier ID
     * @return the updated {@link Subscription} entity.
     */
    @PatchMapping("/{subId}")
    public Subscription modifySubscription(@PathVariable Long subId, @Valid @RequestBody ModifySubscriptionRequest request) {
        return subscriptionService.modifySubscription(subId, request.newPlanId(), request.newTierId());
    }

    /**
     * Cancels an existing active subscription.
     *
     * @param subId the ID of the subscription to cancel
     * @return the updated {@link Subscription} entity reflecting the CANCELLED status.
     */
    @PutMapping("/{subId}/cancel")
    public Subscription cancelSubscription(@PathVariable Long subId) {
        return subscriptionService.cancelSubscription(subId);
    }

    /**
     * Retrieves all currently active subscriptions for a given user.
     *
     * @param userId the ID of the user to look up
     * @return a list of active {@link Subscription}s belonging to the user.
     */
    @GetMapping("/user/{userId}")
    public List<Subscription> getUserSubscriptions(@PathVariable Long userId) {
        return subscriptionService.getActiveSubscriptionsForUser(userId);
    }
}
