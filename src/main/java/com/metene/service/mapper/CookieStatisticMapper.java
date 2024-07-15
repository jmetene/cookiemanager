package com.metene.service.mapper;

import com.metene.domain.entity.CookieStatistics;
import com.metene.service.dto.Statistic;

import java.time.LocalDateTime;
import java.util.List;


public class CookieStatisticMapper {
    private CookieStatisticMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CookieStatistics toEntity(Statistic statistic) {
        return CookieStatistics
                .builder()
                .estado(statistic.getEstado())
                .fecha(LocalDateTime.parse(statistic.getFecha()))
                .plataforma(statistic.getPlataforma())
                .pais(statistic.getPais())
                .build();
    }

    public static List<CookieStatistics> toEntityList(List<Statistic> statistics) {
        return statistics.stream().map(CookieStatisticMapper::toEntity).toList();
    }

    public static Statistic toDTO(CookieStatistics cookieStatistics) {
        return Statistic
                .builder()
                .estado(cookieStatistics.getEstado())
                .fecha(cookieStatistics.getFecha().toString())
                .plataforma(cookieStatistics.getPlataforma())
                .pais(cookieStatistics.getPais())
                .build();
    }
}
