package com.firstclub.membership.service;

import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.repository.TierRepository;
import com.firstclub.membership.repository.UserRepository;
import com.firstclub.membership.strategy.TierEvaluationStrategy;
import com.firstclub.membership.constant.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Membership Catalog Service Tests")
class MembershipCatalogServiceTest {

    @Mock
    private TierRepository tierRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TierEvaluationStrategy strategy1;
    @Mock
    private TierEvaluationStrategy strategy2;

    private MembershipCatalogService catalogService;

    @BeforeEach
    void setUp() {
        catalogService = new MembershipCatalogService(null, tierRepository, userRepository, List.of(strategy1, strategy2));
    }

    @Test
    @DisplayName("Should filter and return only the tiers the user is eligible for")
    void shouldReturnOnlyEligibleTiers() {
        User user = User.builder().id(1L).build();
        Tier tier1 = Tier.builder().id(1L).name(AppConstants.TIER_SILVER).build();
        Tier tier2 = Tier.builder().id(2L).name(AppConstants.TIER_GOLD).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tierRepository.findAll()).thenReturn(List.of(tier1, tier2));

        // Mock strategies: User is eligible for tier1 but not tier2
        when(strategy1.isEligible(user, tier1)).thenReturn(true);
        when(strategy2.isEligible(user, tier1)).thenReturn(true);

        when(strategy1.isEligible(user, tier2)).thenReturn(true);
        when(strategy2.isEligible(user, tier2)).thenReturn(false);

        List<Tier> eligibleTiers = catalogService.getEligibleTiersForUser(1L);

        assertEquals(1, eligibleTiers.size());
        assertEquals(AppConstants.TIER_SILVER, eligibleTiers.get(0).getName());
    }
}
