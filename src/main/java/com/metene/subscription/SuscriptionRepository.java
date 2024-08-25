package com.metene.subscription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<Subscription> findByNameIgnoreCase(String name);
}
