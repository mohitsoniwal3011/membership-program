package com.firstclub.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Represents a User in the FirstClub Membership program.
 * <p>
 * This entity stores user details along with metrics like total orders 
 * and total order value which are crucial for evaluating membership tier eligibility.
 * </p>
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Unique identifier for the User.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The full name of the user.
     */
    private String name;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The total number of orders the user has made. Used for Tier evaluation.
     */
    private Integer totalOrders;

    /**
     * The total monetary value of all orders made by the user. Used for Tier evaluation.
     */
    private BigDecimal totalOrderValue;

    /**
     * A grouping classification for the user (e.g., NEW, EXISTING, VIP).
     */
    private String cohortType;
}
