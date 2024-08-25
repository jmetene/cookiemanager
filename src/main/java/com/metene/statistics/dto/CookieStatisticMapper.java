package com.metene.statistics.dto;

import com.metene.statistics.CookieStatistics;

import java.time.LocalDate;
import java.util.List;


public class CookieStatisticMapper {
    private CookieStatisticMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CookieStatistics toEntity(Statistic statistic) {
        return CookieStatistics
                .builder()
                .estado(statistic.getEstado())
                .fecha(LocalDate.parse(statistic.getFecha()))
                .plataforma(statistic.getPlataforma())
                .pais(statistic.getPais())
                .build();
    }

    public static List<CookieStatistics> toEntityList(List<Statistic> statistics) {
        return statistics.stream().map(CookieStatisticMapper::toEntity).toList();
    }

    public static StatisticResponse toDTO(CookieStatistics cookieStatistics) {
        return StatisticResponse
                .builder()
                .nombreCookie(cookieStatistics.getCookie().getName())
                .tipoCookie(String.valueOf(cookieStatistics.getCookie().getType()))
                .estado(cookieStatistics.getEstado())
                .fecha(cookieStatistics.getFecha().toString())
                .plataforma(cookieStatistics.getPlataforma())
                .pais(cookieStatistics.getPais())
                .build();
    }

    public static List<StatisticResponse> toDTOList(List<CookieStatistics> cookieStatisticsList) {
        return cookieStatisticsList.stream().map(CookieStatisticMapper::toDTO).toList();
    }
}
