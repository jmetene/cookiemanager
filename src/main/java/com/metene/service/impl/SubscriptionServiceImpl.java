package com.metene.service.impl;

import com.metene.domain.entity.Subscription;
import com.metene.domain.repository.SuscriptionRepository;
import com.metene.service.SubscriptionService;
import com.metene.service.dto.SubscriptionRequest;
import com.metene.service.dto.SubscriptionResponse;
import com.metene.service.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SuscriptionRepository suscriptionRepository;

    @Override
    public SubscriptionResponse getSuscription(String name) {
        Subscription subscription =  suscriptionRepository.findByNameIgnoreCase(name).orElseThrow();
        return SubscriptionMapper.toDTO(subscription);
    }

    @Override
    public List<SubscriptionResponse> getAllSuscriptionsPlan() {
        return suscriptionRepository.findAll().stream().map(SubscriptionMapper::toDTO).toList();
    }

    @Override
    public void addSuscription(SubscriptionRequest suscription) {
        Subscription subscriptionEntity = SubscriptionMapper.toEntity(suscription);
        suscriptionRepository.save(subscriptionEntity);
    }

    @Override
    public void deleteSuscription(String name) {
        Subscription subscriptionToDelete = suscriptionRepository.findByNameIgnoreCase(name).orElseThrow();
        suscriptionRepository.delete(subscriptionToDelete);
    }

    @Override
    public void updateSuscription(SubscriptionRequest suscription, String planeName) {
        Subscription subscriptionToUpdate = suscriptionRepository.findByNameIgnoreCase(planeName).orElseThrow();

        Subscription subscriptionEntity = SubscriptionMapper.toEntity(suscription);
        subscriptionEntity.setId(subscriptionToUpdate.getId());

        suscriptionRepository.save(subscriptionEntity);
    }
}
