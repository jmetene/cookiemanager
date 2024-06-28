package com.metene.domain.repository;

import com.metene.domain.entity.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookieRepository extends JpaRepository<Cookie, Long> {
}
