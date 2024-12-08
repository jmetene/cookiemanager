package com.metene.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    @Query("SELECT domain FROM Domain domain WHERE domain.user.email = ?1")
    List<Domain> findByUserName(String email);
    @Query("SELECT domain FROM Domain domain WHERE domain.nombre = ?1")
    Optional<Domain> findByNombre(String name);
}
