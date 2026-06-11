package com.firstclub.membership.strategy;

import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cohort Strategy Tests")
class CohortStrategyTest {

    private final CohortStrategy strategy = new CohortStrategy();

    @Test
    @DisplayName("Should pass eligibility when there is no cohort restriction on the tier")
    void shouldBeEligibleWhenAllowedCohortIsNull() {
        Tier tier = Tier.builder().name("SILVER").build();
        User user = User.builder().cohortType("NEWBIE").build();
        assertTrue(strategy.isEligible(user, tier));
    }

    @Test
    @DisplayName("Should pass eligibility when the user's cohort matches the tier's allowed cohort")
    void shouldBeEligibleWhenCohortMatches() {
        Tier tier = Tier.builder().name("PLATINUM").allowedCohort("VIP").build();
        User user = User.builder().cohortType("VIP").build();
        assertTrue(strategy.isEligible(user, tier));
    }

    @Test
    @DisplayName("Should fail eligibility when the user's cohort does not match the tier's allowed cohort")
    void shouldNotBeEligibleWhenCohortDoesNotMatch() {
        Tier tier = Tier.builder().name("PLATINUM").allowedCohort("VIP").build();
        User user = User.builder().cohortType("REGULAR").build();
        assertFalse(strategy.isEligible(user, tier));
    }

    @Test
    @DisplayName("Should fail eligibility when the user's cohort is null but the tier requires one")
    void shouldNotBeEligibleWhenUserCohortIsNull() {
        Tier tier = Tier.builder().name("PLATINUM").allowedCohort("VIP").build();
        User user = User.builder().cohortType(null).build();
        assertFalse(strategy.isEligible(user, tier));
    }
}
