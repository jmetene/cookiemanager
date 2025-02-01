package com.metene.srciptbanner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScriptBannerRepository extends JpaRepository<ScriptBanner, Long> {
    Optional<ScriptBanner> findByApiKey(String apiKey);
}
