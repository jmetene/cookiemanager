package com.metene.domain.repository;

import com.metene.domain.entity.CookieBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CookieBannerRepository extends JpaRepository<CookieBanner, Long> {
    @Query("SELECT banner FROM CookieBanner banner WHERE banner.user.username = ?1")
    Optional<List<CookieBanner>> findCookieBannerByUserName(String username);
}
