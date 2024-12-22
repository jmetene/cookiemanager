package com.metene.statistics;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomStatisticsRepository {
    List<CookieStatistics> finadAllStatistics(@Param("idDominio") Long idDominio,
                                              @Param("estado") String estado,
                                              @Param("fechaDesde") String fechaDesde,
                                              @Param("fechaHasta") String fechaHasta,
                                              @Param("plataforma") String plataforma,
                                              @Param("plataforma") String pais);
}
