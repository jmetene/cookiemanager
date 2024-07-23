package com.metene.service.utiles;

import com.metene.common.Constantes;
import com.metene.domain.entity.CookieStatistics;
import com.metene.domain.repository.CookieStatisticsRepository;
import com.metene.service.dto.StatisticResponse;
import com.metene.service.mapper.CookieStatisticMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticsUtils {
    private StatisticsUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<StatisticResponse> getCookieStatisticsByTwoParameters(Long domainId,
                                                                       Long cookieId,
                                                                       List<Map.Entry<String, String[]>> parametros,
                                                                       CookieStatisticsRepository repository) {

        Map<String, String> mapOfQueryParams = extractQueryParams(parametros);

        String pais = mapOfQueryParams.getOrDefault(Constantes.COUNTRY, null);
        String estado = mapOfQueryParams.getOrDefault(Constantes.STATUS, null);
        String fechaFin = mapOfQueryParams.getOrDefault(Constantes.END_DATE, null);
        String plataforma = mapOfQueryParams.getOrDefault(Constantes.PLATFORM, null);
        String fechaInicio = mapOfQueryParams.getOrDefault(Constantes.START_DATE, null);

        // Lista para almacenar las estadísitcas
        List<CookieStatistics> statistics = new ArrayList<>();
        // Usamos el mapa para evitar usar las condiciones con IF
        Map<String, Runnable> queryMap = new HashMap<>();

        queryMap.put("fechaInicio_fechaFin", () -> statistics.addAll(repository
                .findStatisticsBetweenPeriod(domainId, cookieId, LocalDate.parse(fechaInicio),
                        LocalDate.parse(fechaFin)).orElseThrow()));
        queryMap.put("fechaInicio_estado", () -> statistics.addAll(repository
                .findStatisticsByFechaInicioAndEstado(domainId, cookieId, LocalDate.parse(fechaInicio), estado)
                .orElseThrow()));
        queryMap.put("fechaInicio_plataforma", () -> statistics.addAll(repository
                .findStatisticsByFechaInicioAndPlataforma(domainId, cookieId, LocalDate.parse(fechaInicio), plataforma)
                .orElseThrow()));
        queryMap.put("fechaInicio_pais", () -> statistics.addAll(repository
                .findStatisticsByFechaInicioAndPais(domainId, cookieId, LocalDate.parse(fechaInicio), pais)
                .orElseThrow()));
        queryMap.put("estado_pais", () -> statistics.addAll(repository
                .findStatisticsByEstadoAndPais(domainId, cookieId, estado, pais)
                .orElseThrow()));
        queryMap.put("estado_plataforma", () -> statistics.addAll(repository
                .findStatisticsByEstadoAndPlataforma(domainId, cookieId, estado, plataforma)
                .orElseThrow()));
        queryMap.put("pais_plataforma", () -> statistics.addAll(repository
                .findStatisticsByPaisAndPlataforma(domainId, cookieId, pais, plataforma)
                .orElseThrow()));

        // Construimos la clave para el mapa de consultas
        String key = builtKeyForQuery(estado, pais, plataforma, fechaInicio, fechaFin);
        // Ejecutamos la consulta correspondiente si existe
        if (queryMap.containsKey(key)) queryMap.get(key).run();

        return CookieStatisticMapper.toDTOList(statistics);
    }

    public static List<StatisticResponse> getCookieStatisticsByFourParameters(Long domainId,
                                                                        Long cookieId,
                                                                        List<Map.Entry<String, String[]>> parametros,
                                                                        CookieStatisticsRepository repository) {

        Map<String, String> mapOfQueryParams = extractQueryParams(parametros);

        String pais = mapOfQueryParams.getOrDefault(Constantes.COUNTRY, null);
        String estado = mapOfQueryParams.getOrDefault(Constantes.STATUS, null);
        String fechaFin = mapOfQueryParams.getOrDefault(Constantes.END_DATE, null);
        String plataforma = mapOfQueryParams.getOrDefault(Constantes.PLATFORM, null);
        String fechaInicio = mapOfQueryParams.getOrDefault(Constantes.START_DATE, null);

        // Lista para almacenar las estadísitcas
        List<CookieStatistics> statistics = new ArrayList<>();
        // Usamos el mapa para evitar usar las condiciones con IF
        Map<String, Runnable> queryMap = new HashMap<>();

        queryMap.put("fechaInicio_fechaFin_estado_plataforma", () -> statistics.addAll(repository
                .findStatisticsByEstadoAndPlataformaBetweenPeriod(domainId, cookieId, LocalDate.parse(fechaInicio),
                        LocalDate.parse(fechaFin), estado, plataforma).orElseThrow()));
        queryMap.put("fechaInicio_fechaFin_estado_pais", () -> statistics.addAll(repository
                .findStatisticsByEstadoAndPaisBetweenPeriod(domainId, cookieId, LocalDate.parse(fechaInicio),
                        LocalDate.parse(fechaFin), estado, pais).orElseThrow()));
        queryMap.put("fechaInicio_fechaFin_pais_plataforma", () -> statistics.addAll(repository
                .findStatisticsByPlataformaAndPaisBetweenPeriod(domainId, cookieId, LocalDate.parse(fechaInicio),
                        LocalDate.parse(fechaFin), pais, plataforma).orElseThrow()));
        queryMap.put("fechaInicio_estado_pais_plataforma", () -> statistics.addAll(repository
                .findStatisticsByFechaAndEstadoAndPlataformaAndPais(domainId, cookieId, LocalDate.parse(fechaInicio),
                        estado, pais, plataforma).orElseThrow()));

        // Construimos la clave para el mapa de consultas
        String key = builtKeyForQuery(estado, pais, plataforma, fechaInicio, fechaFin);
        // Ejecutamos la consulta correspondiente si existe
        if (queryMap.containsKey(key)) queryMap.get(key).run();

        return CookieStatisticMapper.toDTOList(statistics);
    }

    public static List<StatisticResponse> getCookieStatisticsThreeParameters(Long domainId,
                                                                       Long cookieId,
                                                                       List<Map.Entry<String, String[]>> parametros,
                                                                       CookieStatisticsRepository repository) {

        // Extraemos los parámetros de la consulta en un mapa
        Map<String, String> mapOfQueryParams = extractQueryParams(parametros);

        // Obtenemos los valores de los parámetros con valores por defecto null
        String pais = mapOfQueryParams.getOrDefault(Constantes.COUNTRY, null);
        String estado = mapOfQueryParams.getOrDefault(Constantes.STATUS, null);
        String fechaFin = mapOfQueryParams.getOrDefault(Constantes.END_DATE, null);
        String plataforma = mapOfQueryParams.getOrDefault(Constantes.PLATFORM, null);
        String fechaInicio = mapOfQueryParams.getOrDefault(Constantes.START_DATE, null);

        // Lista para almacenar las estadísticas
        List<CookieStatistics> statistics = new ArrayList<>();

        // Mapa para almacenar las consultas correspondientes a cada combinación de parámetros
        Map<String, Supplier<List<CookieStatistics>>> queryMap = new HashMap<>();

        // Definimos las consultas y las almacenamos en el mapa
        queryMap.put("fechaInicio_fechaFin_estado", () -> repository
                .findStatisticsByEstadoBetweenPeriod(domainId, cookieId, LocalDate.parse(fechaInicio),
                        LocalDate.parse(fechaFin), estado).orElseThrow());

        queryMap.put("fechaInicio_fechaFin_plataforma", () -> repository
                .findStatisticsByPlataformaBetweenPeriod(domainId, cookieId, LocalDate.parse(fechaInicio),
                        LocalDate.parse(fechaFin), plataforma).orElseThrow());

        queryMap.put("fechaInicio_fechaFin_pais", () -> repository
                .findStatisticsByPaisBetweenPeriod(domainId, cookieId, LocalDate.parse(fechaInicio),
                        LocalDate.parse(fechaFin), pais).orElseThrow());

        queryMap.put("fechaInicio_plataforma_pais", () -> repository
                .findStatisticsByFechaAndPaisAndPlataforma(domainId, cookieId, LocalDate.parse(fechaInicio),
                        plataforma, pais).orElseThrow());

        queryMap.put("fechaInicio_estado_pais", () -> repository
                .findStatisticsByFechaAndPaisAndEstado(domainId, cookieId, LocalDate.parse(fechaInicio),
                        pais, estado).orElseThrow());

        queryMap.put("fechaInicio_estado_plataforma", () -> repository
                .findStatisticsByFechaAndPlataformaAndEstado(domainId, cookieId, LocalDate.parse(fechaInicio),
                        plataforma, estado).orElseThrow());

        queryMap.put("estado_pais_plataforma", () -> repository
                .findStatisticsByPlataformaAndPaisAndEstado(domainId, cookieId, plataforma, estado, pais)
                .orElseThrow());

        // Construimos la clave para el mapa de consultas
        String key = builtKeyForQuery(estado, pais, plataforma, fechaInicio, fechaFin);

        // Ejecutamos la consulta correspondiente si existe
        if (queryMap.containsKey(key)) statistics.addAll(queryMap.get(key).get());

        // Convertimos la lista de estadísticas a DTO
        return CookieStatisticMapper.toDTOList(statistics);
    }


    public static List<StatisticResponse> getCookieStatisticsByAllfilters(Long domainId,
                                                                    Long cookieId,
                                                                    List<Map.Entry<String, String[]>> parametros,
                                                                    CookieStatisticsRepository repository) {

        Map<String, String> mapOfQueryParams = extractQueryParams(parametros);

        String fechaInicio = mapOfQueryParams.get(Constantes.START_DATE);
        String fechaFin = mapOfQueryParams.get(Constantes.END_DATE);
        String estado = mapOfQueryParams.get(Constantes.STATUS);
        String pais = mapOfQueryParams.get(Constantes.COUNTRY);
        String plataforma = mapOfQueryParams.get(Constantes.PLATFORM);

        List<CookieStatistics> statistics = repository
                .findCookieStatisticsByAllParameters(domainId, cookieId, LocalDate.parse(fechaInicio),
                        LocalDate.parse(fechaFin), estado, pais, plataforma)
                .orElseThrow();

        return CookieStatisticMapper.toDTOList(statistics);
    }

    public static List<StatisticResponse> getCookieStatisticsByOneParameter(Long domain, Long cookie,
                                                                      Map.Entry<String, String[]> parametro,
                                                                      CookieStatisticsRepository repository) {
        // Extraemos el parametro de consulta
        String queryParam = clean(parametro.getValue());
        return switch (parametro.getKey()) {
            case Constantes.STATUS -> getCookieStatisticsByEstado(domain, cookie, queryParam, repository);
            case Constantes.COUNTRY -> getCookieStatisticsByPais(domain, cookie, queryParam, repository);
            case Constantes.PLATFORM -> getCookieStatisticsByPlataforma(domain, cookie, queryParam, repository);
            case Constantes.START_DATE -> getCookieStatisticsByFechaInicio(domain, cookie, queryParam, repository);
            default -> Collections.emptyList();
        };
    }

    private static List<StatisticResponse> getCookieStatisticsByFechaInicio(Long domain, Long cookie, String fechaInicio,
                                                                            CookieStatisticsRepository repository) {
        List<CookieStatistics> statistics = repository
                .findByDomainAndCookieIdAndFechaInicio(domain, cookie, LocalDate.parse(fechaInicio))
                .orElseThrow();

        return CookieStatisticMapper.toDTOList(statistics);
    }

    private static List<StatisticResponse> getCookieStatisticsByPlataforma(Long domain, Long cookie, String plataforma,
                                                                           CookieStatisticsRepository repository) {
        List<CookieStatistics> statistics = repository
                .findByDomainAndCookieIdAndPlataforma(domain, cookie, plataforma)
                .orElseThrow();

        return CookieStatisticMapper.toDTOList(statistics);
    }

    private static List<StatisticResponse> getCookieStatisticsByPais(Long domain, Long cookie, String pais,
                                                                     CookieStatisticsRepository repository) {
        List<CookieStatistics> statistics = repository
                .findByDomainAndCookieIdAndPais(domain, cookie, pais)
                .orElseThrow();

        return CookieStatisticMapper.toDTOList(statistics);
    }

    private static List<StatisticResponse> getCookieStatisticsByEstado(Long domain, Long cookie, String estado,
                                                                       CookieStatisticsRepository repository) {
        List<CookieStatistics> statistics = repository
                .findByDomainAndCookieIdAndEstado(domain, cookie, estado)
                .orElseThrow();

        return CookieStatisticMapper.toDTOList(statistics);
    }

    public static Map<String, String> extractQueryParams(List<Map.Entry<String, String[]>> parametros) {
        Map<String, String> mapOfqueryParamsValue = new HashMap<>();
        parametros.forEach(stringEntry -> mapOfqueryParamsValue.put(stringEntry.getKey(), clean(stringEntry.getValue())));

        return mapOfqueryParamsValue;
    }

    private static String clean(String[] entryValue) {
        return Arrays.toString(entryValue).replace("[", "").replace("]", "");
    }

    public static String builtKeyForQuery(String estado, String pais, String plataforma, String fechaInicio, String fechaFin) {
        return Stream
                .of(fechaInicio != null ? Constantes.START_DATE : null, fechaFin != null ? Constantes.END_DATE : null,
                        estado != null ? Constantes.STATUS : null, pais != null ? Constantes.COUNTRY : null,
                        plataforma != null ? Constantes.PLATFORM : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("_"));
    }
}
