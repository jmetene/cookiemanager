package com.metene.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    @Query("SELECT domain FROM Domain domain WHERE domain.user.username = ?1")
    List<Domain> findByUserName(String username);
}
