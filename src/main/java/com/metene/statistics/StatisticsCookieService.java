package com.metene.statistics;

import com.metene.statistics.dto.CookieStatisticMapper;
import com.metene.statistics.dto.StatisticResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsCookieService {
    // Repositorio para realizar consulta a la BD de estadísiticas de cookie
    private final StatisticsCookieRepository statisticsCookieRepository;

    /**
     * Recupera la información de estadísitica de una sola cookie
     * @param domainId      Identificación del dominio
     * @param estado        filtro por estado: ACEPTADO o RECHAZADO
     * @param fechaDesde    filtro por fecha (representa el límite inferior del rango)
     * @param fechaHasta    filtro por fecha (representa el límite superior del rango)
     * @param plataforma    filtro por plataforma (WEB o MOBILE)
     * @param pais          filtro por país
     * @return {@code List<StatisticResponse>}
     */
    public List<StatisticResponse> fetchStatistics(Long domainId, String estado, String fechaDesde,
                                                   String fechaHasta, String plataforma, String pais) {

        if (domainId == null) {
            throw new IllegalStateException("Domain id cannot be null");
        }

        final List<CookieStatistics> cookieStatistics = statisticsCookieRepository
                .finadAllStatistics(domainId, estado, fechaDesde, fechaHasta, plataforma, pais);

        return CookieStatisticMapper.toDTOList(cookieStatistics);
    }
}
