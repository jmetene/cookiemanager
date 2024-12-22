package com.metene.statistics;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsCookieRepository extends JpaRepository<CookieStatistics, Integer>,
        CustomStatisticsRepository {
}
