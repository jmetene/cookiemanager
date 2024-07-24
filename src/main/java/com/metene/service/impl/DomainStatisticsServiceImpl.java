package com.metene.service.impl;

import com.metene.common.Constantes;
import com.metene.domain.entity.Cookie;
import com.metene.domain.entity.CookieStatistics;
import com.metene.domain.repository.CookieRepository;
import com.metene.domain.repository.CookieStatisticsRepository;
import com.metene.service.DomainStatisticsService;
import com.metene.service.dto.StatisticResponse;
import com.metene.service.mapper.CookieStatisticMapper;
import com.metene.service.utiles.DomainStatisticsUtils;
import com.metene.service.utiles.CookieStatisticsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class DomainStatisticsServiceImpl implements DomainStatisticsService {

    private final CookieStatisticsRepository statisticsRepository;
    private final CookieRepository cookieRepository;

    @Override
    public List<StatisticResponse> getCookieStatistics(Long domainId, Long cookieId, List<Map.Entry<String, String[]>> parametros) {
        // Inicializamos las listas de estadísticas y respuestas
        List<CookieStatistics> statistics = null;
        List<StatisticResponse> statisticResponse = null;

        // Si no hay parámetros, obtenemos las estadísticas directamente del repositorio
        if (parametros.isEmpty()) {
            statistics = statisticsRepository.findByDomainIdAndCookieId(domainId, cookieId).orElseThrow();
        }

        // Si se obtuvieron estadísticas, las convertimos a DTO
        if (statistics != null) {
            statisticResponse = CookieStatisticMapper.toDTOList(statistics);
        }

        // Usamos un switch para manejar los diferentes tamaños de parámetros
        switch (parametros.size()) {
            case Constantes.ONE:
                statisticResponse = CookieStatisticsUtils.getCookieStatisticsByOneParameter(domainId, cookieId,
                        parametros.get(0), statisticsRepository);
                break;
            case Constantes.TWO:
                statisticResponse = CookieStatisticsUtils.getCookieStatisticsByTwoParameters(domainId, cookieId, parametros,
                        statisticsRepository);
                break;
            case Constantes.THREE:
                statisticResponse = CookieStatisticsUtils.getCookieStatisticsThreeParameters(domainId, cookieId, parametros,
                        statisticsRepository);
                break;
            case Constantes.FOUR:
                statisticResponse = CookieStatisticsUtils.getCookieStatisticsByFourParameters(domainId, cookieId, parametros,
                        statisticsRepository);
                break;
            case Constantes.FIVE:
                statisticResponse = CookieStatisticsUtils.getCookieStatisticsByAllfilters(domainId, cookieId, parametros,
                        statisticsRepository);
                break;
            default:
                // Manejo de caso por defecto si es necesario
                break;
        }
        return statisticResponse;
    }

    @Override
    public List<StatisticResponse> getStatisticsByDomain(Long domainId, List<Map.Entry<String, String[]>> parametros) {
        // Recuperamos todas las cookies asociadas al dominio
        List<Cookie> cookies = cookieRepository.findByDomain(domainId).orElseThrow();

        final List<CookieStatistics> statistics = new ArrayList<>();

        // Obtenemos todas las estadísitcas de las cookies del dominio
        cookies.forEach(cookie -> statistics.addAll(cookie.getStatisticsList()));

        return DomainStatisticsUtils.getDomainStatistics(statistics, parametros);
    }
}
