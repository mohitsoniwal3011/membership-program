package com.firstclub.membership.service;

import com.firstclub.membership.entity.*;
import com.firstclub.membership.repository.PlanRepository;
import com.firstclub.membership.repository.SubscriptionRepository;
import com.firstclub.membership.repository.TierRepository;
import com.firstclub.membership.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import com.firstclub.membership.constant.ErrorMessages;
import com.firstclub.membership.constant.AppConstants;
import com.firstclub.membership.exception.BusinessValidationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Subscription Service Tests")
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PlanRepository planRepository;
    @Mock
    private TierRepository tierRepository;
    @Mock
    private MembershipCatalogService catalogService;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private User testUser;
    private Plan testPlan;
    private Tier testTier;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).name("Test User").build();
        testPlan = Plan.builder().id(1L).durationDays(30).build();
        testTier = Tier.builder().id(1L).name(AppConstants.TIER_GOLD).build();
    }

    @Test
    @DisplayName("Should enforce defense-in-depth and throw exception if user is ineligible for tier")
    void shouldThrowExceptionWhenUserNotEligibleForSubscription() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(planRepository.findById(1L)).thenReturn(Optional.of(testPlan));
        when(tierRepository.findById(1L)).thenReturn(Optional.of(testTier));
        when(catalogService.isUserEligibleForTier(testUser, testTier)).thenReturn(false);

        BusinessValidationException exception = assertThrows(BusinessValidationException.class, () -> {
            subscriptionService.subscribe(1L, 1L, 1L);
        });

        assertEquals(ErrorMessages.USER_NOT_ELIGIBLE_FOR_TIER + AppConstants.TIER_GOLD, exception.getMessage());
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully create a new ACTIVE subscription when user is eligible")
    void shouldSuccessfullySubscribeWhenEligible() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(planRepository.findById(1L)).thenReturn(Optional.of(testPlan));
        when(tierRepository.findById(1L)).thenReturn(Optional.of(testTier));
        when(catalogService.isUserEligibleForTier(testUser, testTier)).thenReturn(true);
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(i -> i.getArguments()[0]);

        Subscription result = subscriptionService.subscribe(1L, 1L, 1L);

        assertNotNull(result);
        assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());
        assertEquals(testUser, result.getUser());
        assertEquals(testTier, result.getTier());
        assertEquals(LocalDate.now(), result.getStartDate());
        assertEquals(LocalDate.now().plusDays(30), result.getEndDate());
    }
}
