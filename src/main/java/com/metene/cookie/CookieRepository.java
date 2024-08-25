package com.metene.cookie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CookieRepository extends JpaRepository<Cookie, Long> {
    @Query("SELECT cookie FROM Cookie cookie WHERE cookie.domain.id = ?1")
    Optional<List<Cookie>> findByDomain(Long domain);
    @Query("SELECT cookie FROM Cookie cookie WHERE cookie.name = ?1 AND cookie.provider = ?2")
    Optional<Cookie> findByNameAndProvider(String name, String provider);
}
