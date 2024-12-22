package com.metene.statistics.impl;

import com.metene.domain.Domain;
import com.metene.statistics.CookieStatistics;
import com.metene.statistics.CustomStatisticsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CustomStatisticsRepositoryImpl implements CustomStatisticsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CookieStatistics> finadAllStatistics(Long idDominio, String estado, String fechaInicio, String fechaFin,
                                                     String plataforma, String pais) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CookieStatistics> cbQuery = cb.createQuery(CookieStatistics.class);

        Root<CookieStatistics> root = cbQuery.from(CookieStatistics.class);

        // Hacemos los Joins con DOMAIN
        Join<CookieStatistics, Domain> domainJoin = root.join("domain", JoinType.INNER);

        // Crear la lista de predicados (condiciones dinámicas)
        List<Predicate> predicates = new ArrayList<>();

        // Filtros dinámicos según los parámetros
        if (idDominio != null) {
            predicates.add(cb.equal(domainJoin.get("id"), idDominio));
        }

        if (StringUtils.isNotEmpty(estado)) {
            predicates.add(cb.like(cb.upper(root.get("estado")), "%" + estado.toUpperCase() + "%"));
        }

        if (StringUtils.isNotEmpty(pais)) {
            predicates.add(cb.like(cb.upper(root.get("pais")), "%" + pais.toUpperCase() + "%"));
        }

        if (StringUtils.isNotEmpty(plataforma)) {
            predicates.add(cb.like(cb.upper(root.get("plataforma")), "%" + plataforma.toUpperCase() + "%"));
        }

        if (StringUtils.isNotEmpty(fechaInicio) && StringUtils.isEmpty(fechaFin)) {
            LocalDate date = Date.valueOf(fechaInicio).toLocalDate();
            predicates.add(cb.equal(root.get("fecha"), date));
        }

        if (StringUtils.isNotEmpty(fechaInicio) && StringUtils.isNotEmpty(fechaFin)) {
            LocalDate initDate = Date.valueOf(fechaInicio).toLocalDate();
            LocalDate endDate = Date.valueOf(fechaFin).toLocalDate();
            predicates.add(cb.between(root.get("fecha"), initDate, endDate));
        }

        cbQuery.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<CookieStatistics> query = entityManager.createQuery(cbQuery);
        return query.getResultList();
    }
}
