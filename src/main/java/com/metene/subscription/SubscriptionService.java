package com.metene.subscription;

import com.metene.subscription.dto.SubscriptionMapper;
import com.metene.subscription.dto.SubscriptionRequest;
import com.metene.subscription.dto.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private static  final String MESSAGE = "Subscription not found";
    private final SuscriptionRepository suscriptionRepository;

    public SubscriptionResponse getSuscription(String name) {
        Subscription subscription =  suscriptionRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new SubscriptionNotFoundException(MESSAGE));
        return SubscriptionMapper.toDTO(subscription);
    }

    public List<SubscriptionResponse> getAllSuscriptionsPlan() {
        return suscriptionRepository.findAll().stream().map(SubscriptionMapper::toDTO).toList();
    }

    public void addSuscription(SubscriptionRequest suscription) {
        Subscription subscriptionEntity = SubscriptionMapper.toEntity(suscription);
        suscriptionRepository.save(subscriptionEntity);
    }

    public void deleteSuscription(String name) {
        Subscription subscriptionToDelete = suscriptionRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new SubscriptionNotFoundException(MESSAGE));
        suscriptionRepository.delete(subscriptionToDelete);
    }

    public void updateSuscription(SubscriptionRequest suscription, String planeName) {
        Subscription subscriptionToUpdate = suscriptionRepository.findByNameIgnoreCase(planeName)
                .orElseThrow(() -> new SubscriptionNotFoundException(MESSAGE));

        Subscription subscriptionEntity = SubscriptionMapper.toEntity(suscription);
        subscriptionEntity.setId(subscriptionToUpdate.getId());

        suscriptionRepository.save(subscriptionEntity);
    }
}
