package com.firstclub.membership.dto;

/**
 * Data Transfer Object (DTO) containing the payload for modifying an existing subscription.
 *
 * @param newPlanId the ID of the new plan to switch to (optional if only upgrading tier)
 * @param newTierId the ID of the new tier to switch to (optional if only changing plan)
 */
public record ModifySubscriptionRequest(Long newPlanId, Long newTierId) {
}
