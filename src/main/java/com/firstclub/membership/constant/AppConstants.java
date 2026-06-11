package com.firstclub.membership.constant;

/**
 * Core application constants to eliminate magic strings.
 */
public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    // Plans
    public static final String PLAN_MONTHLY = "MONTHLY";
    public static final String PLAN_QUARTERLY = "QUARTERLY";
    public static final String PLAN_YEARLY = "YEARLY";

    // Tiers
    public static final String TIER_SILVER = "SILVER";
    public static final String TIER_GOLD = "GOLD";
    public static final String TIER_PLATINUM = "PLATINUM";

    // Cohorts
    public static final String COHORT_NEW = "NEW";
    public static final String COHORT_EXISTING = "EXISTING";
    public static final String COHORT_VIP = "VIP";
    
    // API Endpoints
    public static final String API_V1_CATALOG = "/api/v1/catalog";
    public static final String API_V1_SUBSCRIPTIONS = "/api/v1/subscriptions";
}
