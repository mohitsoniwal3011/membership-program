package com.firstclub.membership.entity;

/**
 * Enum defining the various lifecycle states of a Subscription.
 */
public enum SubscriptionStatus {
    /**
     * Subscription requested but not yet fully processed.
     */
    PENDING,

    /**
     * Subscription is currently active and granting benefits.
     */
    ACTIVE,

    /**
     * Subscription was voluntarily or forcefully cancelled.
     */
    CANCELLED,

    /**
     * Subscription naturally expired past its end date.
     */
    EXPIRED
}
