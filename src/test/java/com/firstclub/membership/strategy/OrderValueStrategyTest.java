package com.firstclub.membership.strategy;

import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.constant.AppConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order Value Strategy Tests")
class OrderValueStrategyTest {
    private final OrderValueStrategy strategy = new OrderValueStrategy();

    @Test
    @DisplayName("Should pass eligibility when there is no minimum order value requirement")
    void shouldBeEligibleWhenMinOrderValueIsNull() {
        Tier tier = Tier.builder().name(AppConstants.TIER_SILVER).build();
        User user = User.builder().totalOrderValue(BigDecimal.ZERO).build();
        assertTrue(strategy.isEligible(user, tier));
    }

    @Test
    @DisplayName("Should pass eligibility when user meets the minimum order value")
    void shouldBeEligibleWhenUserMeetsMinOrderValue() {
        Tier tier = Tier.builder().name(AppConstants.TIER_GOLD).minOrderValue(new BigDecimal("100.0")).build();
        User user = User.builder().totalOrderValue(new BigDecimal("150.0")).build();
        assertTrue(strategy.isEligible(user, tier));
    }

    @Test
    @DisplayName("Should fail eligibility when user does not meet the minimum order value")
    void shouldNotBeEligibleWhenUserDoesNotMeetMinOrderValue() {
        Tier tier = Tier.builder().name(AppConstants.TIER_GOLD).minOrderValue(new BigDecimal("100.0")).build();
        User user = User.builder().totalOrderValue(new BigDecimal("50.0")).build();
        assertFalse(strategy.isEligible(user, tier));
    }
}
