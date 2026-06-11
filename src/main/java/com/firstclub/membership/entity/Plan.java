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
 * Represents a temporal Membership Plan (e.g., Monthly, Quarterly, Yearly).
 * <p>
 * Defines the duration and base price of a membership cycle.
 * </p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    /**
     * Unique identifier for the Plan.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The name of the plan (e.g., MONTHLY, QUARTERLY, YEARLY).
     */
    private String name;

    /**
     * The duration of the plan in days.
     */
    private Integer durationDays;

    /**
     * The base price for the plan duration, before tier multipliers.
     */
    private BigDecimal basePrice;
}
