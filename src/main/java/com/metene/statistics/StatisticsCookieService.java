package com.metene.statistics;

import com.metene.cookie.Cookie;
import com.metene.cookie.CookieRepository;
import com.metene.domain.Domain;
import com.metene.domain.DomainRepository;
import com.metene.statistics.dto.CookieStatisticMapper;
import com.metene.statistics.dto.Statistic;
import com.metene.statistics.dto.StatisticResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsCookieService {
    // Repositorio para realizar consulta a la BD de estadísiticas de cookie
    private final StatisticsCookieRepository statisticsCookieRepository;
    private final CookieRepository cookieRepository;
    private final DomainRepository domainRepository;

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

    /**
     * Guarda las estadísiticas de una cookie en la base de datos
     * @param statistics listado con las estadísiticas
     */
    public void cargarEstadisticas(List<Statistic> statistics) {
        statistics.forEach(statistic -> {
            CookieStatistics cookieStatistics = CookieStatisticMapper.toEntity(statistic);
            Cookie cookie = cookieRepository.findById(statistic.getCookieId())
                    .orElseThrow(() -> new EntityNotFoundException("Cookie not found"));

            Domain domain = domainRepository.findById(statistic.getDomainId())
                    .orElseThrow(() -> new EntityNotFoundException("Domain not found"));

            cookieStatistics.setCookie(cookie);
            cookieStatistics.setDomain(domain);

            statisticsCookieRepository.saveAndFlush(cookieStatistics);
        });
    }
}
