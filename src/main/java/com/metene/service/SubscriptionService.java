package com.metene.service;

import com.metene.service.dto.SubscriptionRequest;
import com.metene.service.dto.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {
    void addSuscription(SubscriptionRequest suscription);
    void deleteSuscription(String name);
    void updateSuscription(SubscriptionRequest suscription, String planeName);
    SubscriptionResponse getSuscription(String name);
    List<SubscriptionResponse> getAllSuscriptionsPlan();
}
