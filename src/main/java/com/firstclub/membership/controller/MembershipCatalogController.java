package com.firstclub.membership.controller;

import com.firstclub.membership.entity.Plan;
import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.service.MembershipCatalogService;
import com.firstclub.membership.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller responsible for providing public and user-specific catalog information 
 * regarding available membership plans and tiers.
 */
@RestController
@RequestMapping(AppConstants.API_V1_CATALOG)
@RequiredArgsConstructor
public class MembershipCatalogController {
    
    private final MembershipCatalogService catalogService;

    /**
     * Retrieves all available membership plans (e.g., Monthly, Quarterly, Yearly).
     *
     * @return a list of all {@link Plan} entities.
     */
    @GetMapping("/plans")
    public List<Plan> getPlans() {
        return catalogService.getAllPlans();
    }

    /**
     * Retrieves all available membership tiers and their default benefits.
     * Note: This does not take into account whether a specific user is eligible for them.
     *
     * @return a list of all {@link Tier} entities.
     */
    @GetMapping("/tiers")
    public List<Tier> getTiers() {
        return catalogService.getAllTiers();
    }

    /**
     * Retrieves the specific membership tiers that a given user is eligible to subscribe to,
     * based on their historical metrics evaluated against the Strategy rules.
     *
     * @param userId the ID of the user requesting their eligible tiers
     * @return a list of {@link Tier} entities the user is allowed to select.
     */
    @GetMapping("/eligible-tiers/{userId}")
    public List<Tier> getEligibleTiers(@PathVariable Long userId) {
        return catalogService.getEligibleTiersForUser(userId);
    }
}
