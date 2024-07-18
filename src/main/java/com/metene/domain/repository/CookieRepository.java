package com.metene.domain.repository;

import com.metene.domain.entity.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CookieRepository extends JpaRepository<Cookie, Long> {
    @Query("SELECT cookie FROM Cookie cookie WHERE cookie.domain.id = ?1")
    Optional<List<Cookie>> findByDomain(Long domain);
    @Query("SELECT cookie FROM Cookie cookie WHERE cookie.domain = ?1 and  cookie.name = ?2")
    Optional<Cookie> findByDomainAndName(Long id, String cookieName);
}
