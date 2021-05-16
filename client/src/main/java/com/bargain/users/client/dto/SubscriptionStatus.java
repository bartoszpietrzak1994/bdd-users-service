package com.bargain.users.client.dto;

import java.util.Arrays;

public enum SubscriptionStatus {
    BRONZE,
    SILVER,
    GOLD,
    PLATINUM;

    public static SubscriptionStatus of(String status) {
        return Arrays.stream(SubscriptionStatus.values())
                .filter(subscriptionStatus -> subscriptionStatus.name().equalsIgnoreCase(status))
                .findFirst()
                .orElse(null);
    }
}
