package com.firstclub.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Represents a Membership Tier (e.g., Silver, Gold, Platinum).
 * <p>
 * This entity captures both the benefits provided by the tier (like free delivery)
 * and the requirements/criteria necessary for a user to be eligible for it.
 * </p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tier {

    /**
     * Unique identifier for the Tier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The name of the tier (e.g., SILVER, GOLD, PLATINUM).
     */
    private String name;

    /**
     * Indicates whether the tier grants free delivery benefits.
     */
    private Boolean freeDelivery;

    /**
     * The additional discount percentage applied to eligible items for this tier.
     */
    private BigDecimal discountPercentage;

    /**
     * Indicates whether the tier grants early access to sales.
     */
    private Boolean earlyAccess;

    /**
     * Indicates whether the tier includes priority customer support.
     */
    private Boolean prioritySupport;

    /**
     * The multiplier applied to a Plan's base price to determine the final cost of this tier.
     */
    private BigDecimal priceMultiplier;
    
    /**
     * Eligibility criteria: The minimum number of historical orders required to unlock this tier.
     */
    private Integer minOrders;

    /**
     * Eligibility criteria: The minimum total order value required to unlock this tier.
     */
    private BigDecimal minOrderValue;

    /**
     * Eligibility criteria: The specific cohort a user must belong to (e.g., "VIP"). 
     * Null means no cohort restriction.
     */
    private String allowedCohort;
}
