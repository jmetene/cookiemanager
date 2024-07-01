package com.metene.domain.repository;

import com.metene.domain.entity.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CookieRepository extends JpaRepository<Cookie, Long> {
    @Query("SELECT cookie FROM Cookie cookie WHERE cookie.name = ?1 and  cookie.user.username = ?2")
    Optional<Cookie> findByNameAndUser(String cookieName, String username);
    @Query("SELECT cookie FROM Cookie cookie WHERE cookie.user.username = ?1")
    Optional<List<Cookie>> findByUser(String username);
}
