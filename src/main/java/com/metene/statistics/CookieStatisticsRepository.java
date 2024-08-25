package com.metene.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CookieStatisticsRepository extends JpaRepository<CookieStatistics, Long> {
    @Query("SELECT stat FROM  CookieStatistics stat WHERE stat.cookie.domain.id = ?1 and stat.cookie.id = ?2")
    Optional<List<CookieStatistics>> findByDomainIdAndCookieId(Long domainId, Long cookieId);

    @Query("SELECT s FROM  CookieStatistics as s WHERE s.cookie.domain.id = ?1 and s.cookie.id = ?2 and s.estado = ?3")
    Optional<List<CookieStatistics>> findByDomainAndCookieIdAndEstado(Long domain, Long cookie, String estado);

    @Query("SELECT s FROM  CookieStatistics as s WHERE s.cookie.domain.id = ?1 and s.cookie.id = ?2 and s.pais = ?3")
    Optional<List<CookieStatistics>> findByDomainAndCookieIdAndPais(Long domain, Long cookie, String pais);

    @Query("SELECT s FROM  CookieStatistics as s WHERE s.cookie.domain.id = ?1 and s.cookie.id = ?2 and s.plataforma = ?3")
    Optional<List<CookieStatistics>> findByDomainAndCookieIdAndPlataforma(Long domain, Long cookie, String plataforma);

    @Query("SELECT s FROM  CookieStatistics as s WHERE s.cookie.domain.id = ?1 and s.cookie.id = ?2 and s.fecha = ?3")
    Optional<List<CookieStatistics>> findByDomainAndCookieIdAndFechaInicio(Long domain,
                                                                           Long cookie,
                                                                           LocalDate fechaInicio);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.id = ?2  and  stat.cookie.domain.id = ?1 and " +
            "stat.estado = ?5 and stat.pais = ?6 and stat.plataforma = ?7 AND stat.fecha BETWEEN ?3 and ?4")
    Optional<List<CookieStatistics>> findCookieStatisticsByAllParameters(Long domainId,
                                                                         Long cookieId,
                                                                         LocalDate fechaInicio,
                                                                         LocalDate fechaFin,
                                                                         String estado,
                                                                         String pais,
                                                                         String plataforma);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.id = ?2  and  stat.cookie.domain.id = ?1 and " +
            "stat.estado = ?5 and stat.fecha BETWEEN ?3 and ?4")
    Optional<List<CookieStatistics>> findStatisticsByEstadoBetweenPeriod(Long domainId,
                                                                         Long cookieId,
                                                                         LocalDate fechaIncio,
                                                                         LocalDate fechaFin,
                                                                         String estado);
    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.id = ?2  and  stat.cookie.domain.id = ?1 and " +
            "stat.plataforma = ?5 and stat.fecha BETWEEN ?3 and ?4")
    Optional<List<CookieStatistics>> findStatisticsByPlataformaBetweenPeriod(Long domainId,
                                                                             Long cookieId,
                                                                             LocalDate fechaIncio,
                                                                             LocalDate fechaFin,
                                                                             String plataforma);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.id = ?2  and  stat.cookie.domain.id = ?1 and " +
            "stat.pais = ?5 and stat.fecha BETWEEN ?3 and ?4")
    Optional<List<CookieStatistics>> findStatisticsByPaisBetweenPeriod(Long domainId,
                                                                       Long cookieId,
                                                                       LocalDate fechaIncio,
                                                                       LocalDate fechaFin,
                                                                       String pais);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.id = ?2  and  stat.cookie.domain.id = ?1 and " +
            "stat.pais = ?5 and stat.fecha = ?3 and stat.plataforma = ?4")
    Optional<List<CookieStatistics>> findStatisticsByFechaAndPaisAndPlataforma(Long domainId,
                                                                               Long cookieId,
                                                                               LocalDate fechaIncio,
                                                                               String plataforma,
                                                                               String pais);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id= ?1  and  stat.cookie.id = ?2 and " +
            "stat.plataforma = ?3 and stat.estado = ?4 and stat.pais = ?5")
    Optional<List<CookieStatistics>> findStatisticsByPlataformaAndPaisAndEstado(Long domainId,
                                                                                Long cookieId,
                                                                                String plataforma,
                                                                                String estado,
                                                                                String pais);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id= ?1  and  stat.cookie.id = ?2 and " +
            "stat.fecha = ?3 and stat.pais = ?4 and stat.estado = ?5")
    Optional<List<CookieStatistics>> findStatisticsByFechaAndPaisAndEstado(Long domainId,
                                                                           Long cookieId,
                                                                           LocalDate fecha,
                                                                           String pais,
                                                                           String estado);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id= ?1  and  stat.cookie.id = ?2 and " +
            "stat.fecha = ?3 and stat.plataforma = ?4 and stat.estado = ?5")
    Optional<List<CookieStatistics>> findStatisticsByFechaAndPlataformaAndEstado(Long domainId,
                                                                 Long cookieId,
                                                                 LocalDate fecha,
                                                                 String plataforma,
                                                                 String estado);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.fecha BETWEEN ?3 and ?4 and stat.estado = ?5 and stat.plataforma = ?6")
    Optional<List<CookieStatistics>> findStatisticsByEstadoAndPlataformaBetweenPeriod(Long domainId,
                                                                                      Long cookieId,
                                                                                      LocalDate fechaInicio,
                                                                                      LocalDate fechaFin,
                                                                                      String estado,
                                                                                      String plataforma);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.fecha BETWEEN ?3 and ?4 and stat.estado = ?5 and stat.pais = ?6")
    Optional<List<CookieStatistics>> findStatisticsByEstadoAndPaisBetweenPeriod(Long domainId,
                                                                                Long cookieId,
                                                                                LocalDate fechaInicio,
                                                                                LocalDate fechaFin,
                                                                                String estado,
                                                                                String pais);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.fecha BETWEEN ?3 and ?4 and stat.pais = ?5 and stat.plataforma = ?6")
    Optional<List<CookieStatistics>> findStatisticsByPlataformaAndPaisBetweenPeriod(Long domainId,
                                                                                    Long cookieId,
                                                                                    LocalDate fechaInicio,
                                                                                    LocalDate fechaFin,
                                                                                    String pais,
                                                                                    String plataforma);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.fecha = ?3 and stat.estado = ?4 and stat.pais = ?5 and stat.plataforma = ?6")
    Optional<List<CookieStatistics>> findStatisticsByFechaAndEstadoAndPlataformaAndPais(Long domainId,
                                                                                        Long cookieId,
                                                                                        LocalDate fechaInicio,
                                                                                        String estado,
                                                                                        String pais,
                                                                                        String plataforma);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.fecha BETWEEN ?3 and ?4")
    Optional<List<CookieStatistics>> findStatisticsBetweenPeriod(Long domainId, Long cookieId, LocalDate fechaInicio,
                                                                 LocalDate fechaFin);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.fecha = ?3 and stat.estado = ?4")
    Optional<List<CookieStatistics>> findStatisticsByFechaInicioAndEstado(Long domainId, Long cookieId,
                                                                          LocalDate fechaInicio,
                                                                          String estado);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.fecha = ?3 and stat.plataforma = ?4")
    Optional<List<CookieStatistics>> findStatisticsByFechaInicioAndPlataforma(Long domainId, Long cookieId,
                                                                              LocalDate fechaInicio, String plataforma);


    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.fecha = ?3 and stat.pais = ?4")
    Optional<List<CookieStatistics>> findStatisticsByFechaInicioAndPais(Long domainId, Long cookieId, LocalDate parse,
                                                                        String pais);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.estado = ?3 and stat.pais = ?4")
    Optional<List<CookieStatistics>> findStatisticsByEstadoAndPais(Long domainId, Long cookieId, String estado,
                                                                   String pais);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.estado = ?3 and stat.plataforma = ?4")
    Optional<List<CookieStatistics>> findStatisticsByEstadoAndPlataforma(Long domainId, Long cookieId, String estado,
                                                                         String plataforma);

    @Query("SELECT stat FROM CookieStatistics  stat WHERE stat.cookie.domain.id = ?1 and  stat.cookie.id = ?2 and " +
            "stat.pais = ?3 and stat.plataforma = ?4")
    Optional<List<CookieStatistics>> findStatisticsByPaisAndPlataforma(Long domainId, Long cookieId, String pais,
                                                                       String plataforma);
}
