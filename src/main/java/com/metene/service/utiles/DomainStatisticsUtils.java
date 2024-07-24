package com.metene.service.utiles;

import com.metene.common.Constantes;
import com.metene.domain.entity.CookieStatistics;
import com.metene.service.dto.StatisticResponse;
import com.metene.service.mapper.CookieStatisticMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.metene.service.utiles.StatisticsUtils.builtKeyForQuery;
import static com.metene.service.utiles.StatisticsUtils.extractQueryParams;

public class DomainStatisticsUtils {
    private DomainStatisticsUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<StatisticResponse> getDomainStatistics(List<CookieStatistics> statistics,
                                                              List<Map.Entry<String, String[]>> parametros) {


        // Obtenemos el número de parámetros para las consultas
        int totalParameters = parametros.size();

        return switch (totalParameters) {
            case Constantes.ONE -> getDomainStatisticsWithOneParameter(statistics, parametros);
            case Constantes.TWO -> getDomainStatisticsWithTwoParameters(statistics, parametros);
            case Constantes.THREE -> getDomainStatisticsWithThreeParameters(statistics, parametros);
            case Constantes.FOUR -> getDomainStatisticsWithFourParameters(statistics, parametros);
            case Constantes.FIVE -> getDomainStatisticsWithFiveParameters(statistics, parametros);
            default -> CookieStatisticMapper.toDTOList(statistics);
        };
    }

    private static List<StatisticResponse> getDomainStatisticsWithOneParameter(List<CookieStatistics> statistics,
                                                                               List<Map.Entry<String, String[]>> parametros) {

        // Extraemos los parámetros de la consulta en un mapa
        Map<String, String> mapOfQueryParams = extractQueryParams(parametros);

        // Obtenemos los valores de los parámetros con valores por defecto null
        String pais = mapOfQueryParams.getOrDefault(Constantes.COUNTRY, null);
        String estado = mapOfQueryParams.getOrDefault(Constantes.STATUS, null);
        String fechaFin = mapOfQueryParams.getOrDefault(Constantes.END_DATE, null);
        String plataforma = mapOfQueryParams.getOrDefault(Constantes.PLATFORM, null);
        String fechaInicio = mapOfQueryParams.getOrDefault(Constantes.START_DATE, null);

        // Mapa para almacenar las consultas correspondientes a cada combinación de parámetros
        Map<String, Supplier<List<CookieStatistics>>> queryMap = new HashMap<>();

        // Definimos las consultas y las almacenamos en el mapa
        queryMap.put("fechaInicio", () -> statistics.stream()
                .filter(estadistica -> estadistica.getFecha().isEqual(LocalDate.parse(fechaInicio))).toList());

        queryMap.put("estado", () -> statistics.stream().filter(estadistica -> estadistica.getEstado().equals(estado))
                .toList());

        queryMap.put("pais", () -> statistics.stream().filter(estadistica -> estadistica.getPais().equals(pais))
                .toList());
        queryMap.put("plataforma", () -> statistics.stream()
                .filter(estadistica -> estadistica.getPlataforma().equals(plataforma)).toList());

        // Construimos la clave para el mapa de consultas
        String key = builtKeyForQuery(estado, pais, plataforma, fechaInicio, fechaFin);

        // Devolvemos vacío si no existe la key
        if (!queryMap.containsKey(key)) return List.of();

        // Ejecutamos la consulta correspondiente si existe y
        // Convertimos la lista de estadísticas a DTO
        return CookieStatisticMapper.toDTOList(queryMap.get(key).get());
    }

    private static List<StatisticResponse> getDomainStatisticsWithTwoParameters(List<CookieStatistics> statistics,
                                                                              List<Map.Entry<String, String[]>> parametros) {
        // Extraemos los parámetros de la consulta en un mapa
        Map<String, String> mapOfQueryParams = extractQueryParams(parametros);

        // Obtenemos los valores de los parámetros con valores por defecto null
        String pais = mapOfQueryParams.getOrDefault(Constantes.COUNTRY, null);
        String estado = mapOfQueryParams.getOrDefault(Constantes.STATUS, null);
        String fechaFin = mapOfQueryParams.getOrDefault(Constantes.END_DATE, null);
        String plataforma = mapOfQueryParams.getOrDefault(Constantes.PLATFORM, null);
        String fechaInicio = mapOfQueryParams.getOrDefault(Constantes.START_DATE, null);

        // Mapa para almacenar las consultas correspondientes a cada combinación de parámetros
        Map<String, Supplier<List<CookieStatistics>>> queryMap = new HashMap<>();

        // Definimos las consultas y las almacenamos en el mapa
        queryMap.put("fechaInicio_fechaFin", () -> statistics.stream()
                .filter(estadistica -> estadistica.getFecha().isEqual(LocalDate.parse(fechaInicio)))
//                .filter(estadistica -> estadistica.getFecha().getLong(LocalDate))
                .toList());

        queryMap.put("fechaInicio_estado", () -> statistics.stream()
                .filter(estadistica -> estadistica.getEstado().equals(estado))
                .filter(estadistica -> estadistica.getFecha().isEqual(LocalDate.parse(fechaInicio)))
                .toList());

        queryMap.put("fechaInicio_plataforma", () -> statistics.stream()
                .filter(estadistica -> estadistica.getPlataforma().equals(plataforma))
                .filter(estadistica -> estadistica.getFecha().isEqual(LocalDate.parse(fechaInicio)))
                .toList());

        queryMap.put("fechaInicio_pais", () -> statistics.stream()
                .filter(estadistica -> estadistica.getPais().equals(pais))
                .filter(estadistica -> estadistica.getFecha().isEqual(LocalDate.parse(fechaInicio)))
                .toList());

        queryMap.put("estado_pais", () -> statistics.stream()
                .filter(estadistica -> estadistica.getPais().equals(pais))
                .filter(estadistica -> estadistica.getEstado().equals(estado))
                .toList());

        queryMap.put("estado_plataforma", () -> statistics.stream()
                .filter(estadistica -> estadistica.getPlataforma().equals(plataforma))
                .filter(estadistica -> estadistica.getEstado().equals(estado))
                .toList());

        queryMap.put("pais_plataforma", () -> statistics.stream()
                .filter(estadistica -> estadistica.getPlataforma().equals(plataforma))
                .filter(estadistica -> estadistica.getPais().equals(pais))
                .toList());

        // Construimos la clave para el mapa de consultas
        String key = builtKeyForQuery(estado, pais, plataforma, fechaInicio, fechaFin);

        // Devolvemos vacío si no existe la key
        if (!queryMap.containsKey(key))
            return List.of();

        // Ejecutamos la consulta correspondiente si existe y
        // Convertimos la lista de estadísticas a DTO
        return CookieStatisticMapper.toDTOList(queryMap.get(key).get());
    }

    private static List<StatisticResponse> getDomainStatisticsWithThreeParameters(List<CookieStatistics> statistics,
                                                                                List<Map.Entry<String, String[]>> parametros) {

        return List.of();
    }

    private static List<StatisticResponse> getDomainStatisticsWithFourParameters(List<CookieStatistics> statistics,
                                                                               List<Map.Entry<String, String[]>> parametros) {

        return List.of();
    }

    private static List<StatisticResponse> getDomainStatisticsWithFiveParameters(List<CookieStatistics> statistics,
                                                                               List<Map.Entry<String, String[]>> parametros) {

        return List.of();
    }
}
