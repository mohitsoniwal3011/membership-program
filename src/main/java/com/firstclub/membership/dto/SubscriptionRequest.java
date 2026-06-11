package com.firstclub.membership.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) containing the payload for creating a new subscription.
 *
 * @param userId the ID of the user creating the subscription
 * @param planId the ID of the membership plan selected
 * @param tierId the ID of the membership tier selected
 */
public record SubscriptionRequest(
        @NotNull(message = "User ID is required") Long userId, 
        @NotNull(message = "Plan ID is required") Long planId, 
        @NotNull(message = "Tier ID is required") Long tierId) {
}
