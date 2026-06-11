package com.firstclub.membership.config;

import com.firstclub.membership.entity.Plan;
import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.repository.PlanRepository;
import com.firstclub.membership.repository.TierRepository;
import com.firstclub.membership.repository.UserRepository;
import com.firstclub.membership.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Utility component that seeds the embedded H2 database with initial records 
 * on application startup. This is purely for demonstration and testing purposes.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TierRepository tierRepository;

    /**
     * Executes immediately after the application context is loaded.
     * Seeds Plans, Tiers (with varying requirements), and mock Users.
     *
     * @param args incoming main method arguments
     * @throws Exception on any seeding failure
     */
    @Override
    public void run(String... args) throws Exception {
        if (planRepository.count() > 0) return; // Only seed if empty

        // 1. Seed Plans
        Plan monthly = Plan.builder().name(AppConstants.PLAN_MONTHLY).durationDays(30).basePrice(new BigDecimal("9.99")).build();
        Plan quarterly = Plan.builder().name(AppConstants.PLAN_QUARTERLY).durationDays(90).basePrice(new BigDecimal("25.99")).build();
        Plan yearly = Plan.builder().name(AppConstants.PLAN_YEARLY).durationDays(365).basePrice(new BigDecimal("89.99")).build();
        planRepository.saveAll(List.of(monthly, quarterly, yearly));

        // 2. Seed Tiers with eligibility criteria
        Tier silver = Tier.builder().name(AppConstants.TIER_SILVER)
                .freeDelivery(true).discountPercentage(new BigDecimal("5.0")).earlyAccess(false).prioritySupport(false)
                .priceMultiplier(new BigDecimal("1.0"))
                .minOrders(0).minOrderValue(new BigDecimal("0.0")) // Available to all
                .build();
        
        Tier gold = Tier.builder().name(AppConstants.TIER_GOLD)
                .freeDelivery(true).discountPercentage(new BigDecimal("10.0")).earlyAccess(true).prioritySupport(false)
                .priceMultiplier(new BigDecimal("1.5"))
                .minOrders(5).minOrderValue(new BigDecimal("100.0")) // Needs 5 orders and $100 total
                .build();
        
        Tier platinum = Tier.builder().name(AppConstants.TIER_PLATINUM)
                .freeDelivery(true).discountPercentage(new BigDecimal("20.0")).earlyAccess(true).prioritySupport(true)
                .priceMultiplier(new BigDecimal("2.5"))
                .minOrders(15).minOrderValue(new BigDecimal("500.0")) // Needs 15 orders and $500 total
                .allowedCohort(AppConstants.COHORT_VIP) // CohortStrategy will filter by this
                .build();
        tierRepository.saveAll(List.of(silver, gold, platinum));

        // 3. Seed Users with varying metrics to demonstrate the eligible-tiers API
        User newbie = User.builder().name("Alice Newbie").email("alice@test.com")
                .totalOrders(1).totalOrderValue(new BigDecimal("20.0")).cohortType(AppConstants.COHORT_NEW).build();
        
        User regular = User.builder().name("Bob Regular").email("bob@test.com")
                .totalOrders(8).totalOrderValue(new BigDecimal("150.0")).cohortType(AppConstants.COHORT_EXISTING).build();
        
        User whale = User.builder().name("Charlie Whale").email("charlie@test.com")
                .totalOrders(50).totalOrderValue(new BigDecimal("2000.0")).cohortType(AppConstants.COHORT_VIP).build();
        
        userRepository.saveAll(List.of(newbie, regular, whale));

        log.info("Data seeding completed successfully.");
        log.info("Test User IDs created: 1 (Newbie cohort), 2 (Existing cohort), 3 (VIP cohort)");
    }
}
