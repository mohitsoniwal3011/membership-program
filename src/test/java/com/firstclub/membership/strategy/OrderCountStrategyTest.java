package com.firstclub.membership.strategy;

import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.constant.AppConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order Count Strategy Tests")
class OrderCountStrategyTest {
    private final OrderCountStrategy strategy = new OrderCountStrategy();

    @Test
    @DisplayName("Should pass eligibility when there is no minimum orders requirement")
    void shouldBeEligibleWhenMinOrdersIsNull() {
        Tier tier = Tier.builder().name(AppConstants.TIER_SILVER).build();
        User user = User.builder().totalOrders(0).build();
        assertTrue(strategy.isEligible(user, tier));
    }

    @Test
    @DisplayName("Should pass eligibility when user meets the minimum order count")
    void shouldBeEligibleWhenUserMeetsMinOrders() {
        Tier tier = Tier.builder().name(AppConstants.TIER_GOLD).minOrders(5).build();
        User user = User.builder().totalOrders(6).build();
        assertTrue(strategy.isEligible(user, tier));
    }

    @Test
    @DisplayName("Should fail eligibility when user does not meet the minimum order count")
    void shouldNotBeEligibleWhenUserDoesNotMeetMinOrders() {
        Tier tier = Tier.builder().name(AppConstants.TIER_GOLD).minOrders(5).build();
        User user = User.builder().totalOrders(4).build();
        assertFalse(strategy.isEligible(user, tier));
    }
}
