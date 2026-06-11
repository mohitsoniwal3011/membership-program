package com.firstclub.membership.constant;

/**
 * Constants for common error messages used across the application.
 */
public final class ErrorMessages {
    
    private ErrorMessages() {
        // Prevent instantiation
    }

    public static final String USER_NOT_FOUND = "User not found";
    public static final String PLAN_NOT_FOUND = "Plan not found";
    public static final String TIER_NOT_FOUND = "Tier not found";
    public static final String SUBSCRIPTION_NOT_FOUND = "Subscription not found";
    public static final String USER_NOT_ELIGIBLE_FOR_TIER = "User is not eligible for tier: ";
    public static final String ONLY_ACTIVE_SUBS_MODIFIABLE = "Only active subscriptions can be modified";
    public static final String SUBSCRIPTION_ALREADY_CANCELLED = "Subscription is already cancelled";
}
