package com.metene.service;

import com.metene.service.dto.StatisticResponse;

import java.util.List;
import java.util.Map;

public interface DomainStatisticsService {
    List<StatisticResponse> getCookieStatistics(Long domainId, Long cookieId, List<Map.Entry<String, String[]>> parametros);
    List<StatisticResponse> getStatisticsByDomain(Long domainId, List<Map.Entry<String, String[]>> parametros);
}
