package com.metene.domain.repository;

import com.metene.domain.entity.CookieBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CookieBannerRepository extends JpaRepository<CookieBanner, Long> {
    @Query("SELECT banner FROM CookieBanner banner WHERE banner.domain.id =? 1")
    Optional<CookieBanner> getByDomain(Long domain);
}
