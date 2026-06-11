package com.firstclub.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.firstclub.membership.exception.BusinessValidationException;
import com.firstclub.membership.constant.ErrorMessages;

/**
 * Represents a User's active or past subscription to a Membership Plan and Tier.
 * <p>
 * This is the central associative entity linking a User to their chosen benefits.
 * Employs optimistic locking via the @Version field to prevent concurrent modification issues.
 * </p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    /**
     * Unique identifier for the Subscription.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who owns the subscription.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The selected membership plan (duration/base price).
     */
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    /**
     * The selected membership tier (benefits level).
     */
    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;

    /**
     * The date the subscription commenced.
     */
    private LocalDate startDate;

    /**
     * The date the subscription expires or ends.
     */
    private LocalDate endDate;

    /**
     * The current lifecycle status of the subscription.
     */
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    /**
     * Version field used by JPA for Optimistic Locking to handle concurrent requests gracefully.
     */
    @Version
    private Long version;

    /**
     * Cancels the subscription if it is not already cancelled.
     * Demonstrates OOP Encapsulation by hiding state transitions inside the domain model.
     *
     * @throws BusinessValidationException if already cancelled
     */
    public void cancel() {
        if (this.status == SubscriptionStatus.CANCELLED) {
            throw new BusinessValidationException(ErrorMessages.SUBSCRIPTION_ALREADY_CANCELLED);
        }
        this.status = SubscriptionStatus.CANCELLED;
    }

    /**
     * Updates the plan and recalculates the end date.
     *
     * @param newPlan the new plan to apply
     * @throws BusinessValidationException if the subscription is not active
     */
    public void updatePlan(Plan newPlan) {
        if (this.status != SubscriptionStatus.ACTIVE) {
            throw new BusinessValidationException(ErrorMessages.ONLY_ACTIVE_SUBS_MODIFIABLE);
        }
        this.plan = newPlan;
        this.endDate = LocalDate.now().plusDays(newPlan.getDurationDays());
    }

    /**
     * Updates the tier.
     *
     * @param newTier the new tier to apply
     * @throws BusinessValidationException if the subscription is not active
     */
    public void updateTier(Tier newTier) {
        if (this.status != SubscriptionStatus.ACTIVE) {
            throw new BusinessValidationException(ErrorMessages.ONLY_ACTIVE_SUBS_MODIFIABLE);
        }
        this.tier = newTier;
    }
}
