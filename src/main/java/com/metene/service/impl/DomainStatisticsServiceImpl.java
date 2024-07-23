package com.metene.service.impl;

import com.metene.common.Constantes;
import com.metene.domain.entity.CookieStatistics;
import com.metene.domain.repository.CookieStatisticsRepository;
import com.metene.service.DomainStatisticsService;
import com.metene.service.dto.StatisticResponse;
import com.metene.service.mapper.CookieStatisticMapper;
import com.metene.service.utiles.StatisticsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class DomainStatisticsServiceImpl implements DomainStatisticsService {

    private final CookieStatisticsRepository statisticsRepository;

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
                statisticResponse = StatisticsUtils.getCookieStatisticsByOneParameter(domainId, cookieId,
                        parametros.get(0), statisticsRepository);
                break;
            case Constantes.TWO:
                statisticResponse = StatisticsUtils.getCookieStatisticsByTwoParameters(domainId, cookieId, parametros,
                        statisticsRepository);
                break;
            case Constantes.THREE:
                statisticResponse = StatisticsUtils.getCookieStatisticsThreeParameters(domainId, cookieId, parametros,
                        statisticsRepository);
                break;
            case Constantes.FOUR:
                statisticResponse = StatisticsUtils.getCookieStatisticsByFourParameters(domainId, cookieId, parametros,
                        statisticsRepository);
                break;
            case Constantes.FIVE:
                statisticResponse = StatisticsUtils.getCookieStatisticsByAllfilters(domainId, cookieId, parametros,
                        statisticsRepository);
                break;
            default:
                // Manejo de caso por defecto si es necesario
                break;
        }
        return statisticResponse;
    }
}
