package com.metene.service.mapper;

import com.metene.domain.entity.Subscription;
import com.metene.service.dto.SubscriptionRequest;
import com.metene.service.dto.SubscriptionResponse;

public class SubscriptionMapper {
    private SubscriptionMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Subscription toEntity(SubscriptionRequest subscriptionRequest) {
        return Subscription
                .builder()
                .name(subscriptionRequest.getName().toLowerCase())
                .description(subscriptionRequest.getDescription())
                .price(subscriptionRequest.getPrice())
                .discount(subscriptionRequest.getDiscount())
                .build();
    }

    public static SubscriptionResponse toDTO(Subscription subscription) {
        return SubscriptionResponse
                .builder()
                .name(subscription.getName())
                .description(subscription.getDescription())
                .price(subscription.getPrice())
                .discount(subscription.getDiscount())
                .build();
    }
}
